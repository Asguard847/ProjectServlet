package web.commands;

import web.Constants;
import web.Page;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.POSTFIX;
import static web.Constants.PREFIX;


public class NotFoundCommand implements Command {

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {
        return new Page(PREFIX + "404" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
