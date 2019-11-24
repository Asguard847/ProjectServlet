package dao.impl;

import dao.AssignmentDao;
import dao.ConnectionFactory;
import entity.Assignment;
import entity.Bus;
import entity.Driver;
import entity.Route;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDaoImpl implements AssignmentDao {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ID_COLUMN = "assignments.id";
    private static final String ROUTE_ID_COLUMN = "assignments.route_id";
    private static final String BUS_ID_COLUMN = "assignments.bus_id";
    private static final String DRIVER_ID_COLUMN = "assignments.driver_id";
    private static final String APPROVED_COLUMN = "approved";
    private static final String CREATED_COLUMN = "created";
    private static final String CANCELLED_COLUMN = "cancelled";
    private static final String MODEL_COLUMN = "buses.model";
    private static final String BUS_NUMBER_COLUMN = "buses.number";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";

    private static final String ROUTE_NUMBER_COLUMN = "routes.number";

    private static final String GET_ALL_QUERY = "SELECT assignments.id, approved, cancelled, created, " +
            "routes.number, drivers.first_name, drivers.last_name, buses.number, buses.model FROM assignments " +
            "LEFT OUTER JOIN routes ON assignments.route_id = routes.id " +
            "LEFT OUTER JOIN drivers ON assignments.driver_id = drivers.id " +
            "LEFT OUTER JOIN buses ON assignments.bus_id = buses.id";

    private static final String GET_ALL_FOR_DRIVER_QUERY = "SELECT assignments.id, approved, cancelled, created, " +
            "routes.number, drivers.first_name, drivers.last_name, buses.number, buses.model FROM assignments " +
            "LEFT OUTER JOIN routes ON assignments.route_id = routes.id " +
            "LEFT OUTER JOIN drivers ON assignments.driver_id = drivers.id " +
            "LEFT OUTER JOIN buses ON assignments.bus_id = buses.id WHERE assignments.driver_id = ?;";

    private static final String ADD_QUERY = "INSERT INTO assignments (driver_id, bus_id, route_id, created) " +
            "VALUES (?, ?, ?, ?);";

    private static final String DELETE_QUERY = "DELETE FROM assignments WHERE id = ?;";

    private static final String CANCEL_FOR_DRIVER_QUERY = "UPDATE assignments SET cancelled = ? WHERE driver_id = ?;";

    private static final String GET_NEW_QUERY = "SELECT assignments.id, approved, cancelled, created, " +
            "routes.number, drivers.first_name, drivers.last_name, buses.number, buses.model FROM assignments " +
            "LEFT OUTER JOIN routes ON assignments.route_id = routes.id " +
            "LEFT OUTER JOIN drivers ON assignments.driver_id = drivers.id " +
            "LEFT OUTER JOIN buses ON assignments.bus_id = buses.id WHERE assignments.driver_id = ? " +
            "AND cancelled IS NULL;";

    private static final String SET_APPROVED_QUERY = "UPDATE assignments SET approved = ? WHERE id = ?;";

    @Override
    public List<Assignment> getAllAssignments() {

        List<Assignment> assignments = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Assignment assignment = getAssignmentFromResultSet(resultSet);
                assignments.add(assignment);
            }

        } catch (SQLException e) {
            LOG.error("Could not get all assignments");
        }
        return assignments;
    }

    @Override
    public List<Assignment> getAllAssignmentsForDriver(int driverId) {
        List<Assignment> assignments = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_FOR_DRIVER_QUERY)) {

            statement.setInt(1, driverId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Assignment assignment = getAssignmentFromResultSet(resultSet);
                    assignments.add(assignment);
                }
            }
        } catch (SQLException e) {
            LOG.error("Could not get all assignments");
        }
        return assignments;
    }

    @Override
    public void addAssignment(Assignment assignment) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, assignment.getBus().getDriver().getId());
            statement.setInt(2, assignment.getBus().getId());
            statement.setInt(3, assignment.getBus().getRoute().getId());
            statement.setDate(4, new Date(System.currentTimeMillis()));

            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not add assignment");
        }
    }

    @Override
    public void deleteAssignment(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeUpdate();


        } catch (SQLException e) {
            LOG.error("Could not delete assignment " + id);
        }
    }

    @Override
    public void setApproved(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_APPROVED_QUERY)){

            statement.setDate(1, new Date(System.currentTimeMillis()));
            statement.setInt(2, id);
            statement.executeUpdate();
        }catch (SQLException e) {
            LOG.error("Could not approve assignment " + id);
        }
    }

    @Override
    public void setCancelled(int id) {

    }

    @Override
    public void cancelForDriver(int driverId) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(CANCEL_FOR_DRIVER_QUERY)) {

            statement.setDate(1, new Date(System.currentTimeMillis()));
            statement.setInt(2, driverId);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not cancel assignment for driver: " + driverId);
        }
    }

    @Override
    public Assignment getNewForDriver(int driverId) {

        Assignment assignment = null;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_NEW_QUERY)) {
            statement.setInt(1, driverId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    assignment = getAssignmentFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            LOG.error("Could not get assignment for driver: " + driverId);
        }
        return assignment;
    }

    private Assignment getAssignmentFromResultSet(ResultSet resultSet) throws SQLException {

        Assignment assignment = new Assignment();
        assignment.setId(resultSet.getInt(ID_COLUMN));
        assignment.setApproved(resultSet.getDate(APPROVED_COLUMN));
        assignment.setCancelled(resultSet.getDate(CANCELLED_COLUMN));
        assignment.setCreated(resultSet.getDate(CREATED_COLUMN));

        Route route = new Route();
        route.setNumber(resultSet.getString(ROUTE_NUMBER_COLUMN));
        assignment.setRoute(route);

        Bus bus = new Bus();
        bus.setNumber(resultSet.getString(BUS_NUMBER_COLUMN));
        bus.setModel(resultSet.getString(MODEL_COLUMN));
        assignment.setBus(bus);

        Driver driver = new Driver();
        driver.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
        driver.setLastName(resultSet.getString(LAST_NAME_COLUMN));
        assignment.setDriver(driver);

        return assignment;
    }
}
