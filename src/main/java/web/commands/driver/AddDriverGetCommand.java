package web.commands.driver;

import web.Constants;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.*;

public class AddDriverGetCommand implements Command {
    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {
        return new Page(PREFIX +"addDriver" + POSTFIX);
    }
}
