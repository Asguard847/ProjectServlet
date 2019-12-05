package web.commands.login;

import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {
        request.getSession().invalidate();
        return new Page("/app/login", true);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
