package web.commands.bus;

import entity.Bus;
import service.BusService;
import service.impl.BusServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.*;

public class SetReadyBusCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        int id = (Integer) request.getAttribute("id");
        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        busService.setReady(id);
        Bus bus = busService.getBusById(id);
        request.setAttribute("bus", bus);
        return new Page(PREFIX + "editBus" + POSTFIX);
    }
}
