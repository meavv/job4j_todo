package store;

import model.Category;
import model.Item;
import model.Role;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.function.Function;

public class Hibernate {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public static class Lazy {
        public static final Hibernate HOLDER_INSTANCE = new Hibernate();
    }

    private Hibernate() {

    }

    public static Hibernate getInstance() {
        return Lazy.HOLDER_INSTANCE;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void add(Item item) {
        tx(session -> (session.save(item)));
    }

    public void add(Item item, String[] array) {
        try {
            for (String id : array) {
                Category category = findCategory(Integer.parseInt(id));
                item.addCategory(category);

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        tx(session -> (session.save(item)));
    }

    public void add(User user) {
        tx(session -> (session.save(user)));
    }

    public Item findItem(int id) {
        return tx(session -> (session.get(Item.class, id)));
    }

    public Category findCategory(int id) {
        return tx(session -> (session.get(Category.class, id)));
    }

    public Role findRole(int id) {
        return tx(session -> (session.get(Role.class, id)));
    }

    public User findUser(String email) {
        return tx(session -> {
            String hql = "from model.User where email = :emailParam";
            var query = session.createQuery(hql);
            query.setParameter("emailParam", email);
            return (User) query.uniqueResult();
        });
    }

    public boolean update(int id) {
        return tx(session -> {
            String hql = "update model.Item set done = :doneParam where id = :idParam";
            var item = findItem(id);
            var query = session.createQuery(hql);
            query.setParameter("doneParam", item.changeDone());
            query.setParameter("idParam", item.getId());
            int result = query.executeUpdate();
            return result != 0;
        });
    }

    public List<Item> findAll() {
        return tx(session -> (session.createQuery("from model.Item").list()));
    }

    public List<Category> allCategories() {
        return tx(session -> (session
                .createQuery("select c from Category c", Category.class)).list());
    }
}
