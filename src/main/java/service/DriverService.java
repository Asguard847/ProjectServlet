package service;

import entity.Driver;

import java.util.List;

public interface DriverService {

    List<Driver> getAllDrivers();
    List<Driver> getFreeDrivers();
    Driver getDriverById(int id);
    int addDriver(Driver driver);
    void deleteDriver(int id);
    void updateDriver(Driver driver);
    void setReady(int id);
    void setNotReady(int id);
    void setFree(int id);
    void setNotFree(int id);

}
