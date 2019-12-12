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
    public Page performGet(HttpServletRequest request, ServletContext ctx) {

        BusService service = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        List<Bus> buses = service.getAllBuses();
        request.setAttribute("buses", buses);
        return new Page(PREFIX + "buses" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
