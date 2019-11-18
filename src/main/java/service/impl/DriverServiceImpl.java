package service.impl;

import dao.DriverDao;
import dao.impl.DriverDaoImpl;
import entity.Driver;
import service.DriverService;

import java.util.List;



public class DriverServiceImpl implements DriverService {

    private static DriverDao driverDao;

    static{
        driverDao = new DriverDaoImpl();
    }

    @Override
    public  List<Driver> getAllDrivers() {
        return driverDao.getAllDrivers();
    }

    @Override
    public List<Driver> getFreeDrivers() {
        return driverDao.getFreeDrivers();
    }

    @Override
    public Driver getDriverById(int id) {
        return driverDao.getDriverById(id);
    }

    @Override
    public int addDriver(Driver driver) {
        return driverDao.addDriver(driver);
    }

    @Override
    public void deleteDriver(int id) {
        driverDao.deleteDriver(id);
    }

    @Override
    public void updateDriver(Driver driver) {
        driverDao.updateDriver(driver);
    }

    @Override
    public void setReady(int id) {
        driverDao.setReady(id);
    }

    @Override
    public void setNotReady(int id) {
        driverDao.setNotReady(id);
    }

    @Override
    public void setFree(int id) {
        driverDao.setFree(id);
    }

    @Override
    public void setNotFree(int id) {
        driverDao.setNotFree(id);
    }
}
