package service.impl;

import dao.BusDao;
import dao.impl.BusDaoImpl;
import entity.Bus;
import entity.Driver;
import service.AssignmentService;
import service.BusService;
import service.DriverService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static web.Constants.ASSIGNMENT_SERVICE;
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
    public List<Bus> getAllBuses() {

        return busDao.getAllBuses();
    }

    @Override
    public List<Bus> getReadyForRoute() {
        return busDao.getReadyForRoute();
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
    public void updateBus(Bus bus, int driverId, ServletContext ctx) {

        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        AssignmentService assignmentService =
                (AssignmentServiceImpl) ctx.getAttribute(ASSIGNMENT_SERVICE);

        int id = bus.getId();
        boolean ready = bus.isReady();

        Bus oldBus = getBusById(id);
        Driver oldDriver = oldBus.getDriver();

        if (ready) {
            setReady(id);

            if (driverId == 0) {
                if (oldDriver != null) {
                    oldBus.setDriver(null);
                    driverService.setFree(oldDriver.getId(), true);
                    assignmentService.cancelForDriver(oldDriver.getId());
                }
            } else {

                Driver newDriver = new Driver();
                newDriver.setId(driverId);
                oldBus.setDriver(newDriver);
                driverService.setFree(driverId, false);

                if (oldDriver != null) {
                    driverService.setFree(oldDriver.getId(), true);
                    assignmentService.cancelForDriver(oldDriver.getId());
                }
            }
        } else {
            setNotReady(id);
            oldBus.setDriver(null);
            if (oldDriver != null) {
                driverService.setFree(oldDriver.getId(), true);
                assignmentService.cancelForDriver(oldDriver.getId());
            }
        }
        oldBus.setNumber(bus.getNumber());
        oldBus.setModel(bus.getModel());
        busDao.updateBus(oldBus);
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

    @Override
    public void addBusToRoute(String busId, int routeId) {

        busDao.setRoute(Integer.parseInt(busId), routeId);
    }

    @Override
    public void removeBusFromRoute(int busId) {

        busDao.setRoute(busId, 0);
    }

    private boolean validateBusNumber(String number) {
        return number == null || number.isEmpty();
    }

    private boolean validateBusModel(String model) {
        return model == null || model.isEmpty();
    }

    public static Bus getBusFromRequest(HttpServletRequest request) {

        String number = request.getParameter(NUMBER);
        String model = request.getParameter(MODEL);
        String ready = request.getParameter(READY);

        Bus bus = new Bus();
        bus.setNumber(number);
        bus.setModel(model);

        if(ready == null) {
            bus.setReady(true);
        }else{
            bus.setReady(Boolean.parseBoolean(ready));
        }
        return bus;
    }
}
