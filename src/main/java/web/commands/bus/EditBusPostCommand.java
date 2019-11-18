package web.commands.bus;

import entity.Bus;
import entity.Driver;
import org.apache.log4j.Logger;
import service.BusService;
import service.DriverService;
import service.impl.BusServiceImpl;
import service.impl.DriverServiceImpl;
import web.Page;
import web.ValidationUtils;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;

import static web.Constants.*;

public class EditBusPostCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String NUMBER_MSG = "Number must not be empty";
    private static final String MODEL_MSG = "Model must not be empty";

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        boolean inputCorrect = true;

        int id = (Integer)request.getAttribute("id");
        String number = request.getParameter("number");
        String model = request.getParameter("model");
        String ready = request.getParameter("ready");
        String driverId = request.getParameter("driverSelect");


        if (!ValidationUtils.validateBusNumber(number)) {
            request.setAttribute("numberVal", NUMBER_MSG);
            inputCorrect = false;
        }

        if (!ValidationUtils.validateBusModel(model)) {
            request.setAttribute("modelVal", MODEL_MSG);
            inputCorrect = false;
        }

        if (!inputCorrect) {

            Bus bus = new Bus();
            bus.setNumber(number);
            bus.setModel(model);
            bus.setId(id);

            request.setAttribute("bus", bus);
            return new Page(PREFIX + "editBus" + POSTFIX);

        } else{

            BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
            DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);

            Bus bus = busService.getBusById(id);
            Driver oldDriver = bus.getDriver();

            if(ready.equals("true")){
                busService.setReady(id);

                if(driverId.equals("none")){
                    if(oldDriver!=null) {
                        bus.setDriver(null);
                        driverService.setFree(oldDriver.getId());
                    }
                }else{

                    Driver newDriver = new Driver();
                    int newDriverId = Integer.parseInt(driverId);
                    newDriver.setId(newDriverId);
                    bus.setDriver(newDriver);
                    driverService.setNotFree(newDriverId);

                    if(oldDriver!=null){
                        driverService.setFree(oldDriver.getId());
                    }
                }
            }else{
                busService.setNotReady(id);
                bus.setDriver(null);
                if(oldDriver!=null){
                    driverService.setFree(oldDriver.getId());
                }
            }
            bus.setNumber(number);
            bus.setModel(model);
            busService.updateBus(bus);
            LOG.info("Bus updated: " + id);
            return new Page("/app/admin/buses", true);
        }
    }
}
