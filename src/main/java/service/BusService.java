package service;

import entity.Bus;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BusService {

    List<Bus> getAllBuses();
    List<Bus> getReadyForRoute();
    Bus getBusById(int id);
    int addBus(Bus bus);
    void deleteBus(int id);
    void updateBus(Bus bus, int driverId, ServletContext ctx);
    void setReady(int id);
    void setNotReady(int id);
    boolean validateBusInput(HttpServletRequest request);
    void addBusToRoute(String busId, int routeId);
    void removeBusFromRoute(int busId);

}
