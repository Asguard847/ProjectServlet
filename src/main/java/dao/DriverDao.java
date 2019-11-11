package dao;

import entity.Driver;

import java.util.List;

public interface DriverDao {

    List<Driver> getAllDrivers();
    Driver getDriverById(int id);
    void addDriver(Driver driver);
    void deleteDriver(int id);
    void updateDriver(Driver driver);
}
