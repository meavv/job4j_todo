package store;

import model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Hibernate {

    private  final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private  final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public static class Lazy {
        public static final Hibernate HOLDER_INSTANCE = new Hibernate();
    }

    public static Hibernate getInstance() {
        return Lazy.HOLDER_INSTANCE;
    }

    private Hibernate() {

    }

    public void add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

    public boolean update(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        String hql = "update model.Item set done = :doneParam where description = :descriptionParam";
        var query = session.createQuery(hql);
        query.setParameter("doneParam", item.changeDone());
        query.setParameter("descriptionParam", item.getDescription());
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