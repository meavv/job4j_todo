package store;

import com.google.gson.*;
import model.Category;
import model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Arrays;
import java.util.List;

public class HbmRun {

    private static final Gson GSON = new GsonBuilder().create();

    public static void main(String[] args) {
        String s = "{\"description\":\"1\",\"categories\":[\"\\\"1\\\"/>2\"]}";
        var array = s.substring(s.indexOf("["),
                s.indexOf("]")).replaceAll("\\D", "").split("");
        System.out.println(Arrays.toString(array));
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
