package web.commands.driver;

import entity.Driver;
import service.DriverService;
import service.impl.DriverServiceImpl;
import web.Constants;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static web.Constants.*;

public class DriversCommand implements Command {

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {

        DriverService service = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        List<Driver> drivers = service.getAllDrivers();
        request.setAttribute("drivers", drivers);
        return new Page(PREFIX + "drivers" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
