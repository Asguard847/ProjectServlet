package web.commands.user;

import entity.Assignment;
import entity.Driver;
import entity.User;
import service.AssignmentService;
import service.DriverService;
import service.impl.AssignmentServiceImpl;
import service.impl.DriverServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static web.Constants.*;

public class UserCommand implements Command {

    @Override
    public Page performGet(HttpServletRequest request, ServletContext ctx) {

        DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
        AssignmentService assignmentService =(AssignmentServiceImpl) ctx.getAttribute(ASSIGNMENT_SERVICE);

        User user = (User) request.getSession().getAttribute("user");
        String email = user.getUsername();
        Driver driver = driverService.getDriverByEmail(email);
        request.setAttribute("driver", driver);

        Assignment assignment = assignmentService.getNewForDriver(driver.getId());
        if(assignment!=null){
            request.setAttribute("assignment", assignment);
        }

        return new Page(PREFIX + "user" + POSTFIX);
    }

    @Override
    public Page performPost(HttpServletRequest request, ServletContext ctx) {
        return null;
    }
}
