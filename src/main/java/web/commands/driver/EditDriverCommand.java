package web.commands.driver;

import entity.Driver;
import org.apache.log4j.Logger;
import service.DriverService;
import service.impl.DriverServiceImpl;
import web.Constants;
import web.ImageUtils;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.lang.invoke.MethodHandles;

import static web.Constants.*;
import static web.Constants.DRIVER_SERVICE;

public class EditDriverCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {

        int id = (Integer) request.getAttribute("id");
        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        Driver driver = driverService.getDriverById(id);
        request.setAttribute("driver", driver);

        return new Page(PREFIX + "editDriver" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        boolean driverInputIncorrect = driverService.validateDriverInput(request);

        if (driverInputIncorrect) {
            return new Page(PREFIX + "editDriver" + POSTFIX);
        }

        int id = (Integer) request.getAttribute("id");
        Driver driver = DriverServiceImpl.getDriverFromRequest(request);
        driverService.updateDriver(driver, id);
        ImageUtils.saveImage(request, id);
        LOG.info("Driver updated: " + id);
        return new Page("/app/admin/drivers", true);
    }
}
