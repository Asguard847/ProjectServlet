package web.commands.locale;

import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class LocaleRuCommand implements Command {

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {
       request.getSession().setAttribute("locale", "ru");
        return LocaleUtil.getRedirect(request);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
