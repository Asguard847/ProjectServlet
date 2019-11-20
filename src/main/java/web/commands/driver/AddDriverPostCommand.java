package web.commands.driver;

import org.apache.log4j.Logger;
import service.DriverService;
import service.impl.DriverServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;

import static web.Constants.*;
import static web.Constants.DRIVER_SERVICE;

public class AddDriverPostCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);

        boolean driverInputIncorrect = driverService.validateDriverInput(request);

        if (driverInputIncorrect) {
            return new Page(PREFIX + "addDriver" + POSTFIX);
        }

        driverService.addDriver(request);
        return new Page("/app/admin/drivers", true);

    }
}
