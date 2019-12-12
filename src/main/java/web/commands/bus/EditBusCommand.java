package web.commands.bus;

import entity.Bus;
import entity.Driver;
import org.apache.log4j.Logger;
import service.BusService;
import service.DriverService;
import service.impl.BusServiceImpl;
import service.impl.DriverServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static web.Constants.*;

public class EditBusCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());
    private static final String DRIVER_SELECT = "driverSelect";

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {

        int id = (Integer) request.getAttribute("id");

        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);

        Bus bus = busService.getBusById(id);
        request.setAttribute("bus", bus);

        List<Driver> freeDrivers = driverService.getFreeDrivers();
        request.setAttribute("drivers", freeDrivers);

        return new Page(PREFIX + "editBus" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {

        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        int id = (Integer) request.getAttribute("id");
        Bus bus = BusServiceImpl.getBusFromRequest(request);
        bus.setId(id);
        int driverId = 0;
        String driverIdString = request.getParameter(DRIVER_SELECT);
        if (!driverIdString.equals("none")) {
            driverId = Integer.parseInt(driverIdString);
        }
        busService.updateBus(bus, driverId, ctx);
        LOG.info("Bus updated: " + id);
        return new Page("/app/admin/buses", true);
    }
}
