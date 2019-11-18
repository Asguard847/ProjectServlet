package web.commands.driver;

import entity.Driver;
import service.DriverService;
import service.impl.DriverServiceImpl;
import web.Constants;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.*;
import static web.Constants.DRIVER_SERVICE;

public class EditDriverGetCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        int id = (Integer) request.getAttribute("id");
        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        Driver driver = driverService.getDriverById(id);
        request.setAttribute("driver", driver);

        return new Page(PREFIX + "editDriver" + POSTFIX);
    }
}
