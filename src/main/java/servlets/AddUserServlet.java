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

public class AddUserServlet extends HttpServlet {

    private final static String DB_URL = "jdbc:mysql://localhost:3307/userstore";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private final static String USER = "root";
    private final static String PASS = "rootbond";

    private final static String INSERT_NEW = "INSERT INTO users VALUES (?,?,?,?,?)";

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("addusers.html", pageVariables);


        response.getWriter().println(page);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        System.out.println("Start post");

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String salary = request.getParameter("salary");
        String dateOfBirth = request.getParameter("dateOfBirth");

        response.setContentType("text/html;charset=utf-8");

        PrintWriter writer = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("connection is successfully");

            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW);

            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setInt(4, Integer.parseInt(salary));
            preparedStatement.setDate(5, new Date(Calendar.getInstance().getTimeInMillis()));

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                writer.println("A new user was inserted successfully!");
            } else {
                writer.println("A new user was not inserted!");
            }

            PrintWriter out = response.getWriter();
            out.println("<html><body><b>Successfully INSERTED"
                    + "</b></body></html>");

            response.getWriter().close();
            connection.close();
            preparedStatement.close();
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (FileNotFoundException | SQLException | ClassNotFoundException e) {
            throw new IOException("Can not insert user into database");
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