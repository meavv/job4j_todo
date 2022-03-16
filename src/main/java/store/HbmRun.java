package store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.Category;
import model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Arrays;
import java.util.List;

public class HbmRun {

    private static final Gson GSON = new GsonBuilder().create();

    public static void main(String[] args) {
        String s2 = "{\"description\":\"1\",\"categories\":[\"\\\"1\\\"/>2\"]}";

        var jsonElement = JsonParser.parseString(s2).getAsJsonObject();
        var k  = jsonElement.get("description");
        System.out.println("description: " + k);
        Item item = GSON.fromJson("{description: " + k + "}", Item.class);
        System.out.println(item);






/**
        var ss = Arrays.stream(s.split(",")).toArray();
        Item item = GSON.fromJson(ss[0] + "}", Item.class);
        var array = s.substring(s.indexOf("["),
                s.indexOf("]")).replaceAll("[\\[\\]|\"|/|>|\\\\\\\\]", "").split(",");
 */









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
