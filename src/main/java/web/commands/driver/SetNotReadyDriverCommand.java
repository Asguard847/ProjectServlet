package web.commands.driver;

import service.DriverService;
import service.impl.DriverServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.DRIVER_SERVICE;

public class SetNotReadyDriverCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {
        int id = (Integer) request.getAttribute("id");
        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        driverService.setReady(id, false);
        return new Page("/app/admin/drivers", true);
    }
}
