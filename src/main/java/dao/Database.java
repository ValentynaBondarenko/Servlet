package dao;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Calendar;

public class Database {

    private final static String DB_URL = "jdbc:mysql://localhost:3307/userstore";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private final static String USER = "root";
    private final static String PASS = "rootbond";

    private final static String INSERT_NEW = "INSERT INTO users VALUES (?,?,?,?,?)";
    private final static String DELETE = "DELETE FROM users WHERE id=?";
    private final static String query = "SELECT id, firstName, lastName, salary, dateOfBirth FROM users";

    public Connection connectionToDatabase() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        return connection;
    }

    public void addUsers(int id, String firstName, String lastName, int salary, String dateOfBirth) throws SQLException {
        try (Connection connection = connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW)) {

            preparedStatement.setInt(1, Integer.parseInt(String.valueOf(id)));
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setInt(4, Integer.parseInt(String.valueOf(salary)));
            preparedStatement.setDate(5, new Date(Calendar.getInstance().getTimeInMillis()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Cant connect to database");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Cant insert user into database");
        }
    }


    public void deleteUsers(int id) throws SQLException {
        try (Connection connection = connectionToDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, Integer.parseInt(String.valueOf(id)));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Cant connect to database");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Cant delete user from database");
        }
    }

    public void allUsers(HttpServletResponse response) throws SQLException {

        try (Connection connection = connectionToDatabase();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            try (PrintWriter out = response.getWriter()) {
                out.print(" <table border=\"2\">\n" + "<tr>\n" + "<th>id</th>\n" + "<th>firstName</th>\n" +
                        "<th>lastName</th>\n" + "<th>salary</th>\n" + "<th>dateOfBirth</th>\n" + "\n" + "</tr>");
                out.print(" <caption>USERS FROM DATABASE</caption>");
                while (resultSet.next()) {

                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    int salary = resultSet.getInt("salary");
                    String dateOfBirth = resultSet.getString("dateOfBirth");

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

            } catch (SQLException e) {
                throw new SQLException("Cant connect to database");
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new SQLException("Cant select  all users from database");
        }
    }
}