package dao;

import entity.Bus;

import java.util.List;

public interface BusDao {

    List<Bus> getAllBuses();
    List<Bus> getReadyForRoute();
    Bus getBusById(int id);
    int addBus(Bus bus);
    void deleteBus(int id);
    void updateBus(Bus bus);
    void setReady(int id);
    void setNotReady(int id);
    void setRoute(int busId, int routeId);
}
