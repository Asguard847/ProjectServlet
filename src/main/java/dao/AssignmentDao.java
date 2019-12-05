package dao;

import entity.Assignment;

import java.util.List;

public interface AssignmentDao {

    List<Assignment> getAllAssignments();
    List<Assignment> getAllAssignmentsForDriver(int driverId);
    void addAssignment(Assignment assignment);
    void deleteAssignment(int id);
    void setApproved(int id);
    void setCancelled(int id);
    void cancelForDriver(int driverId);
    Assignment getNewForDriver(int driverId);
}
