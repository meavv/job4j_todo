package store;

import model.Role;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class HbmRun {

    private static final StandardServiceRegistry REG = new StandardServiceRegistryBuilder()
            .configure().build();

    private static final SessionFactory SF = new MetadataSources(REG)
            .buildMetadata().buildSessionFactory();

    public static void main(String[] args) {
        try {
            Role role = create(Role.of("ADMIN"));
            create(User.of("Petr Arsentev", role));
            for (User user : findAll()) {
                System.out.println(user.getName() + " " + user.getRole().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(REG);
        }
    }

    private static <T> T tx(final Function<Session, T> command) {
        final Session session = SF.openSession();
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

    public static <T> T create(T model) {
        tx(session -> session.save(model));
        return model;
    }

    public static Collection<User> findAll() {
        return tx(session -> session.createQuery("from User").list());
    }

}
