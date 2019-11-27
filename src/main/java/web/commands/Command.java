package web.commands;

import web.Page;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public interface Command {

    Page performGet(HttpServletRequest request, ServletContext ctx);
    Page performPost(HttpServletRequest request, ServletContext ctx);
}
