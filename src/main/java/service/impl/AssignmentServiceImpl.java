package service.impl;

import dao.AssignmentDao;
import dao.impl.AssignmentDaoImpl;
import entity.Assignment;
import entity.Bus;
import service.AssignmentService;
import service.BusService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static web.Constants.BUS_SERVICE;

public class AssignmentServiceImpl implements AssignmentService {

    private static AssignmentDao assignmentDao;

    static {
        assignmentDao = new AssignmentDaoImpl();
    }

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentDao.getAllAssignments();
    }

    @Override
    public void addAssignment(HttpServletRequest request, ServletContext ctx) {

        BusService busService = (BusServiceImpl) ctx.getAttribute(BUS_SERVICE);

        String busId = request.getParameter("busSelect");
        Bus bus = busService.getBusById(Integer.parseInt(busId));

        Assignment assignment = new Assignment();
        assignment.setBus(bus);

        assignmentDao.addAssignment(assignment);
    }

    @Override
    public List<Assignment> getAllAssignmentsForDriver(int driverId) {
        return assignmentDao.getAllAssignmentsForDriver(driverId);
    }

    @Override
    public void deleteAssignment(int id) {
        assignmentDao.deleteAssignment(id);
    }

    @Override
    public void setApproved(int id) {
        assignmentDao.setApproved(id);
    }

    @Override
    public void setCancelled(int id) {

    }

    @Override
    public void cancelForDriver(int driverId) {
        assignmentDao.cancelForDriver(driverId);
    }

    @Override
    public Assignment getNewForDriver(int driverId) {
        return assignmentDao.getNewForDriver(driverId);
    }
}
