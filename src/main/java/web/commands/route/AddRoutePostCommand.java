package web.commands.route;

import service.RouteService;
import service.impl.RouteServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.*;

public class AddRoutePostCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {
        RouteService routeService = (RouteServiceImpl) ctx.getAttribute(ROUTE_SERVICE);
        if(routeService.validateRouteInput(request)){
            return new Page(PREFIX + "addRoute" + POSTFIX);
        }

        routeService.addRoute(request);
        return new Page("/app/admin/routes", true);
    }
}
