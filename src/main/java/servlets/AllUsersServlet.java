package servlets;

import dao.Database;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AllUsersServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        Database database = new Database();

        try {
            database.connectionToDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IOException(" Cant connect to database");
        }
        try {
            database.allUsers(response);
        } catch (SQLException e) {
            throw new IOException(" Cant show all users from database");
        }
    }
}


