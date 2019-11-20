package service.impl;

import dao.BusDao;
import dao.impl.BusDaoImpl;
import entity.Bus;
import entity.Driver;
import org.springframework.validation.ValidationUtils;
import service.BusService;
import service.DriverService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static web.Constants.DRIVER_SERVICE;

public class BusServiceImpl implements BusService {

    private static final String NUMBER = "number";
    private static final String MODEL = "model";
    private static final String ID = "id";
    private static final String READY = "ready";
    private static final String DRIVER_SELECT = "driverSelect";

    private static final String NUMBER_MSG = "Number must not be empty";
    private static final String MODEL_MSG = "Model must not be empty";

    private static BusDao busDao;

    static {

        busDao = new BusDaoImpl();
    }

    @Override
    public List<Bus> getAllBuses(HttpServletRequest request) {

        List<Bus> buses = busDao.getAllBuses();
        request.setAttribute("buses", buses);
        return buses;
    }

    @Override
    public Bus getBusById(int id) {

        return busDao.getBusById(id);
    }

    @Override
    public int addBus(HttpServletRequest request) {

        Bus bus = getBusFromRequest(request);
        return busDao.addBus(bus);
    }

    @Override
    public void deleteBus(int id) {
        busDao.deleteBus(id);
    }

    @Override
    public void updateBus(HttpServletRequest request) {

        DriverService driverService = (DriverServiceImpl) request.getServletContext()
                .getAttribute(DRIVER_SERVICE);

        int id = (Integer) request.getAttribute(ID);
        String ready = request.getParameter(READY);
        String driverId = request.getParameter(DRIVER_SELECT);

        Bus bus = getBusById(id);
        Driver oldDriver = bus.getDriver();

        if ("true".equals(ready)) {
            setReady(id);

            if ("none".equals(driverId)) {
                if (oldDriver != null) {
                    bus.setDriver(null);
                    driverService.setFree(oldDriver.getId(), true);
                }
            } else {

                Driver newDriver = new Driver();
                int newDriverId = Integer.parseInt(driverId);
                newDriver.setId(newDriverId);
                bus.setDriver(newDriver);
                driverService.setFree(newDriverId, false);

                if (oldDriver != null) {
                    driverService.setFree(oldDriver.getId(), true);
                }
            }
        } else {
            setNotReady(id);
            bus.setDriver(null);
            if (oldDriver != null) {
                driverService.setFree(oldDriver.getId(), true);
            }
        }
        bus.setNumber(request.getParameter(NUMBER));
        bus.setModel(request.getParameter(MODEL));
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

    @Override
    public boolean validateBusInput(HttpServletRequest request) {

        boolean inputIncorrect = false;
        Bus bus = getBusFromRequest(request);

        if (validateBusNumber(bus.getNumber())) {
            request.setAttribute("numberVal", NUMBER_MSG);
            inputIncorrect = true;
        }

        if (validateBusModel(bus.getModel())) {
            request.setAttribute("modelVal", MODEL_MSG);
            inputIncorrect = true;
        }

        request.setAttribute("bus", bus);

        return inputIncorrect;
    }

    private boolean validateBusNumber(String number) {
        return number == null || number.isEmpty();
    }

    private boolean validateBusModel(String model) {
        return model == null || model.isEmpty();
    }

    private Bus getBusFromRequest(HttpServletRequest request) {

        String number = request.getParameter(NUMBER);
        String model = request.getParameter(MODEL);

        Bus bus = new Bus();
        bus.setNumber(number);
        bus.setModel(model);
        bus.setReady(true);

        return bus;
    }
}
