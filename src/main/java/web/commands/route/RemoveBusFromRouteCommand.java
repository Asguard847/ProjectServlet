package web.commands.route;

import entity.Bus;
import entity.Route;
import service.AssignmentService;
import service.BusService;
import service.RouteService;
import service.impl.AssignmentServiceImpl;
import service.impl.BusServiceImpl;
import service.impl.RouteServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static web.Constants.*;
import static web.Constants.POSTFIX;

public class RemoveBusFromRouteCommand implements Command {
    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        RouteService routeService = (RouteServiceImpl) ctx.getAttribute(ROUTE_SERVICE);
        AssignmentService assignmentService = (AssignmentServiceImpl) ctx.getAttribute(ASSIGNMENT_SERVICE);

        int busId = (Integer)request.getAttribute("id");
        Bus bus = busService.getBusById(busId);
        int routeId = bus.getRoute().getId();

        busService.removeBusFromRoute(request);
        assignmentService.cancelForDriver(bus.getDriver().getId());

        Route route = routeService.getRouteById(routeId);
        request.setAttribute("route", route);

        List<Bus> buses = busService.getReadyForRoute();
        request.setAttribute("buses", buses);

        return new Page(PREFIX + "editRoute" + POSTFIX);

    }
}
