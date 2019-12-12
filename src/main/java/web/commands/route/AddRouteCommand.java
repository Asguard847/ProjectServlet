package web.commands.route;

import entity.Route;
import service.RouteService;
import service.impl.RouteServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.*;

public class AddRouteCommand implements Command {

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {
        return new Page(PREFIX + "addRoute" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        RouteService routeService = (RouteServiceImpl) ctx.getAttribute(ROUTE_SERVICE);
        if(routeService.validateRouteInput(request)){
            return new Page(PREFIX + "addRoute" + POSTFIX);
        }
        Route route = RouteServiceImpl.getRouteFromRequest(request);
        routeService.addRoute(route);
        return new Page("/app/admin/routes", true);
    }
}
