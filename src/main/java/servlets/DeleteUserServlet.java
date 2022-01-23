package servlets;

import pagegenerator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DeleteUserServlet extends HttpServlet {
    private final static String DB_URL = "jdbc:mysql://localhost:3307/userstore";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private final static String USER = "root";
    private final static String PASS = "rootbond";

    private final static String DELETE = "DELETE FROM users WHERE id=?";

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("deleteusers.html", pageVariables);

        response.getWriter().println(page);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        System.out.println("Start delete");

        String id = request.getParameter("id");

        response.setContentType("text/html;charset=utf-8");

        PrintWriter writer = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connection is successfully");

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);

            preparedStatement.setInt(1, Integer.parseInt(id));

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                writer.println("A new user was deleted successfully!");
            } else {
                writer.println("A new user was not deleted!");
            }

            PrintWriter out = response.getWriter();
            out.println("<html><body><b>Successfully Deleted"
                    + "</b></body></html>");

            response.getWriter().close();
            connection.close();
            preparedStatement.close();
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (FileNotFoundException | SQLException | ClassNotFoundException e) {
            throw new IOException("Can not delete user from database");
        }
    }

    static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", request.getParameter("id"));


        return pageVariables;
    }
}


