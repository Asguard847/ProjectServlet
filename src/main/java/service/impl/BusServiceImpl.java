package service.impl;

import dao.BusDao;
import dao.impl.BusDaoImpl;
import entity.Bus;
import service.BusService;

import java.util.List;

public class BusServiceImpl implements BusService {

    private static BusDao busDao;

    static{

        busDao = new BusDaoImpl();
    }

    @Override
    public List<Bus> getAllBuses() {

        return busDao.getAllBuses();
    }

    @Override
    public Bus getBusById(int id) {

        return busDao.getBusById(id);
    }

    @Override
    public int addBus(Bus bus) {
        return busDao.addBus(bus);
    }

    @Override
    public void deleteBus(int id) {
        busDao.deleteBus(id);
    }

    @Override
    public void updateBus(Bus bus) {
        busDao.updateBus(bus);
    }

    @Override
    public void setReady(int id) {
        busDao.setReady(id);
    }

    @Override
    public void setNotReady(int id) {
        busDao.setNotReady(id);
    }
}
