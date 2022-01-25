package servlets;

import dao.Database;
import pagegenerator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DeleteUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("deleteusers.html", pageVariables);

        try {
            response.getWriter().println(page);
        } catch (IOException e) {
            throw new IOException("Cant get data from request");
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");

        response.setContentType("text/html;charset=utf-8");

        Database database = new Database();
        try {
            database.deleteUsers(Integer.parseInt(id));
        } catch (SQLException e) {
            throw new IOException("Cant delete user from database");
        }

        try {
            response.sendRedirect("/users");
        } catch (IOException e) {
            throw new IOException("Cant show update users");
        }
        response.getWriter().close();
    }

    static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", request.getParameter("id"));

        return pageVariables;
    }
}


