package servlet;

import com.google.gson.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import model.Category;
import model.Item;
import model.User;
import store.Hibernate;

public class GreetingServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession sc = req.getSession();
        User user = (User) sc.getAttribute("user");
        var s = req.getReader().readLine();
        JsonObject jsonObject = JsonParser.parseString(s).getAsJsonObject();
        var array = jsonObject.get("categories").getAsString().replaceAll("\\D","")
                .split("");
        Item item = GSON.fromJson("{description: " + jsonObject.get("description") + "}", Item.class);
        item.setDate(new Date());
        item.setUser(user);
        Hibernate.getInstance().add(item, array);
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(item);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(Hibernate.getInstance().findAll());
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();

    }

}