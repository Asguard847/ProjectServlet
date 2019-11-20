package web.commands.bus;

import entity.Bus;
import service.BusService;
import service.impl.BusServiceImpl;
import web.Constants;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static web.Constants.*;

public class BusesCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        BusService service = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        service.getAllBuses(request);
        return new Page(PREFIX + "buses" + POSTFIX);
    }
}
