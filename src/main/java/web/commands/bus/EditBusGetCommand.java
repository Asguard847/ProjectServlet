package web.commands.bus;

import entity.Bus;
import entity.Driver;
import service.BusService;
import service.DriverService;
import service.impl.BusServiceImpl;
import service.impl.DriverServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static web.Constants.*;

public class EditBusGetCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        int id = (Integer) request.getAttribute("id");

        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);

        Bus bus = busService.getBusById(id);
        request.setAttribute("bus", bus);

        List<Driver> freeDrivers = driverService.getFreeDrivers();
        request.setAttribute("drivers", freeDrivers);

        return new Page(PREFIX + "editBus" + POSTFIX);
    }
}
