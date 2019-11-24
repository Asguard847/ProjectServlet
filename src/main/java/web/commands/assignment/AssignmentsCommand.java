package web.commands.assignment;

import entity.Assignment;
import service.AssignmentService;
import service.impl.AssignmentServiceImpl;
import web.Page;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static web.Constants.*;

public class AssignmentsCommand implements Command {

    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        AssignmentService assignmentService =(AssignmentServiceImpl) ctx.getAttribute(ASSIGNMENT_SERVICE);
        List<Assignment> assignments = assignmentService.getAllAssignments();
        request.setAttribute("assignments", assignments);
        return new Page(PREFIX + "assignments" + POSTFIX);
    }
}
