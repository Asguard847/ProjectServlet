package service;

import entity.Route;

import java.util.List;

public interface RouteService  {

    List<Route> getAllRoutes();
    Route getRouteById(int id);
    void addRoute(Route route);
    void deleteRoute(int id);
    void updateRoute(Route route);

}
