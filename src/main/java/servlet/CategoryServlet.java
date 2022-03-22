package servlet;

import model.Category;
import store.Hibernate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        List<Category> list = Hibernate.getInstance().allCategories();
        req.setAttribute("list", list);
        req.getRequestDispatcher("create.jsp").forward(req, resp);
    }
}
