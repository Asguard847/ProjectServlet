package web.commands.driver;

import service.DriverService;
import service.impl.DriverServiceImpl;
import web.Constants;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.*;

public class AddDriverCommand implements Command {
    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {
        return new Page(PREFIX +"addDriver" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);

        boolean driverInputIncorrect = driverService.validateDriverInput(request);

        if (driverInputIncorrect) {
            return new Page(PREFIX + "addDriver" + POSTFIX);
        }

        driverService.addDriver(request);
        return new Page("/app/admin/drivers", true);
    }
}
