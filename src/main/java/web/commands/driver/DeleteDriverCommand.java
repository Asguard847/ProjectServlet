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

    private static final String ID = "id";

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {

        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);

        int id = (Integer) request.getAttribute(ID);
        driverService.deleteDriver(id);
        ImageUtils.deleteImage(request, id);

        return new Page("/app/admin/drivers", true);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
