package web.commands.driver;

import service.AssignmentService;
import service.DriverService;
import service.impl.AssignmentServiceImpl;
import service.impl.DriverServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static web.Constants.ASSIGNMENT_SERVICE;
import static web.Constants.DRIVER_SERVICE;

public class SetNotReadyDriverCommand implements Command {

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {

        int id = (Integer) request.getAttribute("id");

        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        AssignmentService assignmentService = (AssignmentServiceImpl) ctx.getAttribute(ASSIGNMENT_SERVICE);

        assignmentService.cancelForDriver(id);
        driverService.setReady(id, false);

        return new Page("/app/admin/drivers", true);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
