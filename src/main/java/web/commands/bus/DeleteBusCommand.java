package web.commands.bus;

import service.BusService;
import service.impl.BusServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.BUS_SERVICE;

public class DeleteBusCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        int id = (Integer) request.getAttribute("id");
        BusService service = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);
        service.deleteBus(id);
        return new Page ("/app/admin/buses", true);
    }
}
