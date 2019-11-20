package web.commands.bus;

import entity.Bus;
import entity.Driver;
import org.apache.log4j.Logger;
import org.springframework.validation.ValidationUtils;
import service.BusService;
import service.DriverService;
import service.impl.BusServiceImpl;
import service.impl.DriverServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;

import static web.Constants.*;

public class EditBusPostCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        int id = (Integer) request.getAttribute("id");
        busService.updateBus(request);
        LOG.info("Bus updated: " + id);
        return new Page("/app/admin/buses", true);
    }
}

