package service.impl;

import dao.RouteDao;
import dao.impl.RouteDaoImpl;
import entity.Route;
import service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private static RouteDao routeDao;

    static{
        routeDao = new RouteDaoImpl();
    }

    @Override
    public List<Route> getAllRoutes() {
        return routeDao.getAllRoutes();
    }

    @Override
    public Route getRouteById(int id) {
        return routeDao.getRouteById(id);
    }

    @Override
    public void addRoute(Route route) {
        routeDao.addRoute(route);
    }

    @Override
    public void deleteRoute(int id) {
        routeDao.deleteRoute(id);
    }

    @Override
    public void updateRoute(Route route) {
        routeDao.updateRoute(route);
    }
}
