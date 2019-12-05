package dao;

import entity.Driver;

import java.util.List;

public interface DriverDao {

    List<Driver> getAllDrivers();
    List<Driver> getFreeDrivers();
    Driver getDriverById(int id);
    int addDriver(Driver driver);
    void deleteDriver(int id);
    void updateDriver(Driver driver);
    void setReady(int id, boolean ready);
    void setFree(int id, boolean free);
    Driver getDriverByEmail(String email);

}
