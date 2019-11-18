package web.commands.bus;

import entity.Bus;
import org.apache.log4j.Logger;
import service.BusService;
import service.impl.BusServiceImpl;
import web.Page;
import web.ValidationUtils;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;

import static web.Constants.*;
import static web.Constants.POSTFIX;
import static web.Constants.PREFIX;

public class AddBusPostCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String NUMBER_MSG = "Number must not be empty";
    private static final String MODEL_MSG = "Model must not be empty";

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        boolean inputCorrect = true;

        String number = request.getParameter("number");
        String model = request.getParameter("model");

        if (!ValidationUtils.validateBusNumber(number)) {
            request.setAttribute("numberVal", NUMBER_MSG);
            inputCorrect = false;
        }

        if (!ValidationUtils.validateBusModel(model)) {
            request.setAttribute("modelVal", MODEL_MSG);
            inputCorrect = false;
        }

        if (!inputCorrect) {
            return new Page(PREFIX + "addBus" + POSTFIX);
        } else {
            BusService service = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);

            Bus bus = new Bus();
            bus.setNumber(number);
            bus.setModel(model);
            bus.setReady(true);

            int id = service.addBus(bus);
            LOG.info("Bus persisted: " + id);
            return new Page ("/app/admin/buses", true);
        }
    }
}
