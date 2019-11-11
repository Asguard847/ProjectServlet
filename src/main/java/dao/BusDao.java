package dao;

import entity.Bus;

import java.util.List;

public interface BusDao {

    List<Bus> getAllBuses();
    Bus getBusById(int id);
    void addBus(Bus bus);
    void deleteBus(int id);
    void updateBus(Bus bus);
}
