package web.commands.route;

import entity.Bus;
import entity.Route;
import service.BusService;
import service.RouteService;
import service.impl.BusServiceImpl;
import service.impl.RouteServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static web.Constants.*;

public class EditRouteGetCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        RouteService routeService = (RouteServiceImpl) ctx.getAttribute(ROUTE_SERVICE);
        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);

        int routeId = (Integer) request.getAttribute("id");
        Route route = routeService.getRouteById(routeId);
        request.setAttribute("route", route);

        List<Bus> buses = busService.getReadyForRoute();
        request.setAttribute("buses", buses);

        return new Page(PREFIX + "editRoute" + POSTFIX);
    }
}
