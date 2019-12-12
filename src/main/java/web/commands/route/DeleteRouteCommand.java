package web.commands.route;

import service.RouteService;
import service.impl.RouteServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.ROUTE_SERVICE;

public class DeleteRouteCommand implements Command {

    private static final String ID = "id";

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {
        RouteService routeService = (RouteServiceImpl) ctx.getAttribute(ROUTE_SERVICE);
        int id = (Integer) request.getAttribute(ID);
        routeService.deleteRoute(id);
        return new Page("/app/admin/routes", true);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
