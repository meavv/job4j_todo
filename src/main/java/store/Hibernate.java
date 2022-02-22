package store;

import model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

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

    public void add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

    public Item findItem(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public boolean update(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        String hql = "update model.Item set done = :doneParam where id = :idParam";
        var query = session.createQuery(hql);
        query.setParameter("doneParam", item.changeDone());
        query.setParameter("idParam", item.getId());
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return result != 0;
    }

    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
