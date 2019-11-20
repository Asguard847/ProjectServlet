package web.commands.bus;

import entity.Bus;
import org.apache.log4j.Logger;
import org.springframework.validation.ValidationUtils;
import service.BusService;
import service.impl.BusServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;

import static web.Constants.*;

public class AddBusPostCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);

        if (busService.validateBusInput(request)) {
            return new Page(PREFIX + "addBus" + POSTFIX);
        }

        int id = busService.addBus(request);
        LOG.info("Bus persisted: " + id);
        return new Page("/app/admin/buses", true);

    }
}
