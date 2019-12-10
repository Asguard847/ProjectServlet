package web;

import org.apache.log4j.Logger;
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
import java.lang.invoke.MethodHandles;


@WebServlet(value = "/app/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());


    ServletContext ctx;

    @Override
    public void init() throws ServletException {
        ctx = getServletContext();
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
            path = getUriWithoutJsessionId(request);
        }else{
            request.setAttribute("id", id);
            path = getPath(request);
        }


        Command command = CommandFactory.getCommand(path);
        Page page = null;

        if("GET".equals(request.getMethod())){
            page = command.performGet(request, ctx);
        }else{
            page = command.performPost(request, ctx);
        }

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
            LOG.error("Could not get path variable");
        }
        return id;
    }

    private String getLastPathToken(HttpServletRequest request) {
        String uri = getUriWithoutJsessionId(request);
        int lastPath = uri.lastIndexOf('/');
        return uri.substring(lastPath + 1);
    }

    private String getPath(HttpServletRequest request){
        String uri = getUriWithoutJsessionId(request);
        int lastPath = uri.lastIndexOf('/');
        return uri.substring(0, lastPath);
    }

    private String getUriWithoutJsessionId(HttpServletRequest request){
        String fullUri = request.getRequestURI();
        String[] tokens = fullUri.split(";");
        return tokens[0];
    }
}

