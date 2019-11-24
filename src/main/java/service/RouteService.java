package service;

import entity.Route;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RouteService  {

    List<Route> getAllRoutes();
    Route getRouteById(int id);
    void addRoute(HttpServletRequest request);
    void deleteRoute(HttpServletRequest request);
    void updateRoute(HttpServletRequest request);

    boolean validateRouteInput(HttpServletRequest request);

}
