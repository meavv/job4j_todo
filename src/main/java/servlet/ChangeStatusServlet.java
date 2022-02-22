package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import model.Item;
import store.Hibernate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ChangeStatusServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String readLine = req.getReader().readLine().replaceAll("[^0-9\\+]", "");
        int i = Integer.parseInt(readLine);
        boolean rsl = Hibernate.getInstance().update(i);
        System.out.println(rsl);
    }
}
