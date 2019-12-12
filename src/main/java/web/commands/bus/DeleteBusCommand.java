package web.commands.bus;

import entity.Bus;
import service.BusService;
import service.DriverService;
import service.impl.BusServiceImpl;
import service.impl.DriverServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.BUS_SERVICE;
import static web.Constants.DRIVER_SERVICE;

public class DeleteBusCommand implements Command {

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {

        int id = (Integer) request.getAttribute("id");
        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);

        Bus bus = busService.getBusById(id);

        if (bus.getDriver() != null) {
            driverService.setFree(bus.getDriver().getId(), true);
        }

        busService.deleteBus(id);
        return new Page("/app/admin/buses", true);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
