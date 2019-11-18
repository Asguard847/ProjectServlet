package service;

import entity.Bus;

import java.util.List;

public interface BusService {

    List<Bus> getAllBuses();
    Bus getBusById(int id);
    int addBus(Bus bus);
    void deleteBus(int id);
    void updateBus(Bus bus);
    void setReady(int id);
    void setNotReady(int id);
}
