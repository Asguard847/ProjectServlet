package service;

import entity.Driver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DriverService {

    List<Driver> getAllDrivers();
    List<Driver> getFreeDrivers();
    Driver getDriverById(int id);
    void addDriver(HttpServletRequest request);
    void deleteDriver(HttpServletRequest request);
    void updateDriver(HttpServletRequest request);
    void setReady(int id, boolean ready);

    void setFree(int id, boolean ready);

    boolean validateDriverInput(HttpServletRequest request);

}
