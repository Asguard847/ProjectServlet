package web.commands.driver;

import service.DriverService;
import service.impl.DriverServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.DRIVER_SERVICE;

public class SetReadyDriverCommand implements Command {

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {

        int id = (Integer) request.getAttribute("id");
        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        driverService.setReady(id, true);
        return new Page("/app/admin/drivers", true);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
