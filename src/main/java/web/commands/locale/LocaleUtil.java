package web.commands.locale;

import entity.User;
import web.Page;

import javax.servlet.http.HttpServletRequest;

import static web.Constants.ROLE_USER;

public class LocaleUtil {

    public static Page getRedirect(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return new Page("/app/login", true);
        }
        String authority = user.getAuthority();

        if(authority.equals(ROLE_USER)){
            return new Page("/app/user", true);
        }

        return new Page("/app/admin", true);
    }
}
