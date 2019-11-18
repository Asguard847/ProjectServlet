package web.commands;

import web.Page;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public interface Command {

    Page perform(HttpServletRequest request, ServletContext ctx);
}
