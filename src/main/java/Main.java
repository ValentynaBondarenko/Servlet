import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlets.AddUserServlet;
import servlets.AllRequestsServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.AllUsersServlet;
import servlets.DeleteUserServlet;


public class Main {
    public static void main(String[] args) throws Exception {

        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
        AllUsersServlet allUsersServlet = new AllUsersServlet();
        AddUserServlet addUserServlet = new AddUserServlet();
        DeleteUserServlet deleteUserServlet = new DeleteUserServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(allRequestsServlet), "/*");
        context.addServlet(new ServletHolder(allUsersServlet), "/users");
        context.addServlet(new ServletHolder(addUserServlet), "/add");
        context.addServlet(new ServletHolder(deleteUserServlet), "/delete");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();

    }
}

