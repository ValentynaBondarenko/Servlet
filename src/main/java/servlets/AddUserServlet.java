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

public class AddUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("addusers.html", pageVariables);

        try {
            response.getWriter().println(page);
        } catch (IOException exception) {
            throw new IOException("Cant get data from request");
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String salary = request.getParameter("salary");
        String dateOfBirth = request.getParameter("dateOfBirth");

        response.setContentType("text/html;charset=utf-8");

        Database database = new Database();

        try {
            database.connectionToDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IOException(" Cant connect to database");
        }
        try {
            database.addUsers(Integer.parseInt(String.valueOf(id)), firstName, lastName, Integer.parseInt(String.valueOf(salary)), dateOfBirth);
        } catch (SQLException e) {
            throw new IOException(" Cant insert user into database");
        }
        try {
            response.sendRedirect("/users");
        } catch (IOException e) {
            throw new IOException(" Cant update users from database");
        }
    }

    static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", request.getParameter("id"));
        pageVariables.put("firstName", request.getParameter("firstName"));
        pageVariables.put("lastName", request.getParameter("lastName"));
        pageVariables.put("salary", request.getParameter("salary"));

        return pageVariables;
    }
}