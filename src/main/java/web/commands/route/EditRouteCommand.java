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

public class EditRouteCommand implements Command {

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {

        RouteService routeService = (RouteServiceImpl) ctx.getAttribute(ROUTE_SERVICE);
        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);

        int routeId = (Integer) request.getAttribute("id");
        Route route = routeService.getRouteById(routeId);
        request.setAttribute("route", route);

        List<Bus> buses = busService.getReadyForRoute();
        request.setAttribute("buses", buses);

        return new Page(PREFIX + "editRoute" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        RouteService routeService = (RouteServiceImpl) ctx.getAttribute(ROUTE_SERVICE);
        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        AssignmentService assignmentService = (AssignmentServiceImpl) ctx.getAttribute(ASSIGNMENT_SERVICE);

        String busId = request.getParameter("busSelect");

        if (routeService.validateRouteInput(request)) {
            addRouteToRequest(request, routeService);
            addBusesToRequest(request, busService);
            return new Page(PREFIX + "editRoute" + POSTFIX);
        }

        Route route = RouteServiceImpl.getRouteFromRequest(request);
        routeService.updateRoute(route);
        if ("none".equals(busId)) {
            return new Page("/app/admin/routes", true);
        }

        int routeId = (Integer) request.getAttribute("id");
        busService.addBusToRoute(busId, routeId);
        assignmentService.addAssignment(busId, ctx);

        //TODO send user email notification with new assignment

        addRouteToRequest(request, routeService);
        addBusesToRequest(request, busService);

        return new Page(PREFIX + "editRoute" + POSTFIX);
    }

    private void addRouteToRequest(HttpServletRequest request, RouteService routeService) {
        int routeId = (Integer) request.getAttribute("id");
        Route route = routeService.getRouteById(routeId);
        request.setAttribute("route", route);
    }

    private void addBusesToRequest(HttpServletRequest request, BusService busService) {
        List<Bus> buses = busService.getReadyForRoute();
        request.setAttribute("buses", buses);
    }
}

