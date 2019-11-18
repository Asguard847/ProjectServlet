package web.commands;

import web.Page;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;



public class NotFoundCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {
        return new Page("404");
    }
}
