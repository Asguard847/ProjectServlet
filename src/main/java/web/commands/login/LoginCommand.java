package web.commands.login;

import entity.User;
import service.UserService;
import service.impl.UserServiceImpl;
import web.Constants;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.*;

public class LoginCommand implements Command {

    private static final String USER_NOT_FOUND_MSG = "User not found. Please contact system administrator";
    private static final String PASSWORD_INVALID_MSG = "Password is invalid";

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {
        return new Page(PREFIX + "login" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        UserService userService = (UserServiceImpl) ctx.getAttribute(USER_SERVICE);

        User user = userService.getUserByUsername(request);

        if(user == null){
            request.setAttribute("msg", USER_NOT_FOUND_MSG);
            return new Page(PREFIX + "login" + POSTFIX);
        }

        String password = request.getParameter("password");
        boolean matches = userService.validate(user, password);

        if(!matches){
            request.setAttribute("error", PASSWORD_INVALID_MSG);
            return new Page(PREFIX + "login" + POSTFIX);
        }

        user.setPassword(null);
        request.getSession().setAttribute("user", user);

        if(ROLE_ADMIN.equals(user.getAuthority())){
            return new Page("/app/admin", true);
        }

        return new Page("/app/user", true);
    }
}
