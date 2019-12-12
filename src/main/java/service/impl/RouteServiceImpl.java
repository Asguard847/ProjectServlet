package service.impl;

import dao.RouteDao;
import dao.impl.RouteDaoImpl;
import entity.Route;
import service.RouteService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static web.Constants.*;

public class RouteServiceImpl implements RouteService {

    private static final String NUMBER = "number";
    private static final String START = "start";
    private static final String END = "end";
    private static final String LENGTH = "length";
    private static final String ID = "id";

    private static final String NUMBER_MSG = "Number must not be empty";
    private static final String START_MSG = "Start point must not be empty";
    private static final String END_MSG = "End point must not be empty";
    private static final String LENGTH_MSG = "Length input is incorrect";

    private static final String LENGTH_PATTERN = "\\d+";

    private static RouteDao routeDao;

    static{
        routeDao = new RouteDaoImpl();
    }

    @Override
    public List<Route> getAllRoutes() {

        List<Route> routes = routeDao.getAllRoutes();
        for(Route route: routes){
            if(route.getBuses().size()!=0){
                route.setTimeInterval(calculateTimeInterval(route));
            }
        }
        return routes;
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

    @Override
    public boolean validateRouteInput(HttpServletRequest request) {

        String number = request.getParameter(NUMBER);
        String start = request.getParameter(START);
        String end = request.getParameter(END);
        String length = request.getParameter(LENGTH);

        boolean routeInputIncorrect = false;

        if (nullOrEmpty(number)) {
            request.setAttribute("numberVal", NUMBER_MSG);
           routeInputIncorrect = true;
        }

        if (nullOrEmpty(start)) {
            request.setAttribute("startVal", START_MSG);
            routeInputIncorrect = true;
        }

        if (nullOrEmpty(end)) {
            request.setAttribute("endVal", END_MSG);
            routeInputIncorrect = true;
        }

        if (validateLength(length)) {
            request.setAttribute("lengthVal", LENGTH_MSG);
            routeInputIncorrect = true;
        }
        return routeInputIncorrect;
    }

    private int calculateTimeInterval(Route route){
        int length = route.getLength();
        int timeOneSide = length/BUS_SPEED;
        int timeBothSides = timeOneSide * 2;
        return timeBothSides / route.getBuses().size();
    }

    public static Route getRouteFromRequest(HttpServletRequest request){

        String number = request.getParameter(NUMBER);
        String start = request.getParameter(START);
        String end = request.getParameter(END);
        String length = request.getParameter(LENGTH);
        Object routeId = request.getAttribute("id");

        Route route = new Route();
        route.setNumber(number);
        route.setStartPoint(start);
        route.setEndPoint(end);
        route.setLength(Integer.parseInt(length));
        if(routeId!=null){
            route.setId((Integer) routeId);
        }

        return route;
    }

    private boolean nullOrEmpty(String val){
        return val == null || val.isEmpty();
    }

    private boolean matchesPattern(String val, String patternString){
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(val);
        return matcher.matches();
    }

    private boolean validateLength(String length){
        if(nullOrEmpty(length)){
            return true;
        }
        return !matchesPattern(length, LENGTH_PATTERN);
    }


}
