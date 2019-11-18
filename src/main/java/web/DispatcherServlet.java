package web;

import service.impl.BusServiceImpl;
import service.impl.DriverServiceImpl;
import service.impl.RouteServiceImpl;
import web.commands.Command;
import web.commands.CommandFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static web.Constants.*;

@WebServlet(value = "/app/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class DispatcherServlet extends HttpServlet {

    ServletContext ctx;

    @Override
    public void init() throws ServletException {

        ctx = getServletContext();
        ctx.setAttribute(DRIVER_SERVICE, new DriverServiceImpl());
        ctx.setAttribute(BUS_SERVICE, new BusServiceImpl());
        ctx.setAttribute(ROUTE_SERVICE, new RouteServiceImpl());

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = null;
        String lastPathToken = getLastPathToken(request);
        int id = hasPathVariable(lastPathToken);

        if (id < 0) {
            path = request.getRequestURI();
        }else{
            request.setAttribute("id", id);
            path = getPath(request);
        }


        Command command = CommandFactory.getCommand(path, request.getMethod());

        Page page = command.perform(request, ctx);

        if (page.isRedirect()) {
            response.sendRedirect(page.getUrl());
        } else {
            request.getRequestDispatcher(page.getUrl()).forward(request, response);
        }
    }

    private int hasPathVariable(String lastPath) {
        int id = -1;
        try {
            id = Integer.parseInt(lastPath);
        } catch (NumberFormatException e) {
            return id;
        }
        return id;
    }

    private String getLastPathToken(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int lastPath = uri.lastIndexOf('/');
        return uri.substring(lastPath + 1);
    }

    private String getPath(HttpServletRequest request){
        String uri = request.getRequestURI();
        int lastPath = uri.lastIndexOf('/');
        return uri.substring(0, lastPath);
    }
}

