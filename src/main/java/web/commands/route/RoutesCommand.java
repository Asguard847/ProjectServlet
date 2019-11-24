package web.commands.route;

import entity.Route;
import org.apache.log4j.Logger;
import service.BusService;
import service.RouteService;
import service.impl.BusServiceImpl;
import service.impl.RouteServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.lang.invoke.MethodHandles;
import java.util.List;


import static web.Constants.*;

public class RoutesCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {
        RouteService routeService = (RouteServiceImpl) ctx.getAttribute(ROUTE_SERVICE);
        List<Route> routes = routeService.getAllRoutes();
        request.setAttribute("routes", routes);
        return new Page(PREFIX + "routes" + POSTFIX);
    }
}
