package web;

import org.apache.log4j.Logger;
import service.impl.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.invoke.MethodHandles;

import static web.Constants.*;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext ctx = servletContextEvent.getServletContext();
        ctx.setAttribute(DRIVER_SERVICE, new DriverServiceImpl());
        ctx.setAttribute(BUS_SERVICE, new BusServiceImpl());
        ctx.setAttribute(ROUTE_SERVICE, new RouteServiceImpl());
        ctx.setAttribute(ASSIGNMENT_SERVICE, new AssignmentServiceImpl());
        ctx.setAttribute(USER_SERVICE, new UserServiceImpl());
        LOG.info("Servlet context initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.info("Servlet context destroyed");
    }
}
