package web.commands.driver;

import service.DriverService;
import service.impl.DriverServiceImpl;
import web.ImageUtils;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.DRIVER_SERVICE;

public class DeleteDriverCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        int id = (Integer) request.getAttribute("id");
        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        driverService.deleteDriver(id);
        ImageUtils.deleteImage(request, id);
        return new Page("/app/admin/drivers", true);
    }
}
