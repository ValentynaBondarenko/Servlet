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
        AddUserServlet addUserServlet1 = new AddUserServlet();
        DeleteUserServlet deleteUserServlet=new DeleteUserServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);//Это расширение ContextHandler позволяет легко создавать контекст с помощью ServletHandler

        context.addServlet(new ServletHolder(allRequestsServlet), "/*");
        context.addServlet(new ServletHolder(allUsersServlet), "/users");
        context.addServlet(new ServletHolder(addUserServlet1), "/add");
        context.addServlet(new ServletHolder(deleteUserServlet), "/delete");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}

