package store;

import model.Role;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        String name = "name";
        String email = "email";
        String password = "password";
        var role = Hibernate.getInstance().findRole(2);
        if (Hibernate.getInstance().findUser(email) == null) {
            Hibernate.getInstance().add(User.of(name, email, password, role));
        } else {
            System.out.println("ERRRRRRRRR");
        }

    }

    public static <T> T create(T model, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(model);
        session.getTransaction().commit();
        session.close();
        return model;
    }

    public static <T> List<T> findAll(Class<T> cl, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<T> list = session.createQuery("from " + cl.getName(), cl).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
