package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class AllUsersServlet extends HttpServlet {

    // JDBC driver name and database URL
    private final static String DB_URL = "jdbc:mysql://localhost:3307/userstore";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    //  Database credentials
    private final static String USER = "root";
    private final static String PASS = "rootbond";
    private String query;


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(" <table border=\"2\">\n" + "<tr>\n" + "<th>id</th>\n" + "<th>firstName</th>\n" +
                "<th>lastName</th>\n" + "<th>salary</th>\n" + "<th>dateOfBirth</th>\n" + "\n" + "</tr>");

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            Statement statement = connection.createStatement();
            query = "SELECT id, firstName, lastName, salary, dateOfBirth FROM users";
            ResultSet rs = statement.executeQuery(query);

            // Extract data from result set
            out.print(" <caption>USERS FROM DATABASE</caption>");
            while (rs.next()) {

                //Retrieve by column name
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                int salary = rs.getInt("salary");
                String dateOfBirth = rs.getString("dateOfBirth");

                //Display values
                out.print("<tr><td>");
                out.println(id);
                out.print("</td>");
                out.print("<td>");
                out.println(firstName);
                out.print("</td>");
                out.print("<td>");
                out.println(lastName);
                out.print("</td>");
                out.print("<td>");
                out.println(salary);
                out.print("</td>");
                out.print("<td>");
                out.println(dateOfBirth);
                out.print("</td>");
                out.print("</tr>");
            }
            out.println("</body></html>");

            // close all
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {

        }
    }
}


