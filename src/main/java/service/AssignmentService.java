package service;

import entity.Assignment;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AssignmentService {

    List<Assignment> getAllAssignments();
    void addAssignment(String busId, ServletContext ctx);
    List<Assignment> getAllAssignmentsForDriver(int driverId);
    void deleteAssignment(int id);
    void setApproved(int id);
    void setCancelled(int id);
    void cancelForDriver(int driverId);
    Assignment getNewForDriver(int driverId);
}
