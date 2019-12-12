package web.commands.bus;

import entity.Bus;
import org.apache.log4j.Logger;
import service.BusService;
import service.impl.BusServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.lang.invoke.MethodHandles;

import static web.Constants.*;

public class AddBusCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {
        return new Page(PREFIX + "addBus" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);

        if (busService.validateBusInput(request)) {
            return new Page(PREFIX + "addBus" + POSTFIX);
        }
        Bus bus = BusServiceImpl.getBusFromRequest(request);
        int id = busService.addBus(bus);
        LOG.info("Bus persisted: " + id);
        return new Page("/app/admin/buses", true);
    }
}
