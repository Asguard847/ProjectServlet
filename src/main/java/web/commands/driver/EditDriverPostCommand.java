package web.commands.driver;

import org.apache.log4j.Logger;
import service.DriverService;
import service.impl.DriverServiceImpl;
import web.ImageUtils;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.lang.invoke.MethodHandles;

import static web.Constants.*;

public class EditDriverPostCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        boolean driverInputIncorrect = driverService.validateDriverInput(request);

        if (driverInputIncorrect) {
            return new Page(PREFIX + "editDriver" + POSTFIX);
        }

        int id = (Integer) request.getAttribute("id");
        driverService.updateDriver(request);
        ImageUtils.saveImage(request, id);
        LOG.info("Driver updated: " + id);
        return new Page("/app/admin/drivers", true);

    }
}

