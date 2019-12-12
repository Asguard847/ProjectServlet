package service;

import entity.Driver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DriverService {

    List<Driver> getAllDrivers();
    List<Driver> getFreeDrivers();
    Driver getDriverById(int id);
    Driver getDriverByEmail(String email);
    int addDriver(Driver driver);
    void deleteDriver(int id);
    void updateDriver(Driver driver, int id);
    void setReady(int id, boolean ready);

    void setFree(int id, boolean ready);

    boolean validateDriverInput(HttpServletRequest request);

}
