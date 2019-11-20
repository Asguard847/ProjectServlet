package dao.impl;

import dao.BusDao;
import dao.ConnectionFactory;
import entity.Bus;
import entity.Driver;
import entity.Route;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDaoImpl implements BusDao {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ID_COLUMN = "buses.id";
    private static final String MODEL_COLUMN = "buses.model";
    private static final String BUS_NUMBER_COLUMN = "buses.number";
    private static final String ROUTE_ID_COLUMN = "buses.route_id";
    private static final String DRIVER_ID_COLUMN = "buses.driver_id";
    private static final String READY_COLUMN = "buses.ready";

    private static final String FIRST_NAME_COLUMN = "drivers.first_name";
    private static final String LAST_NAME_COLUMN = "drivers.last_name";

    private static final String ROUTE_NUMBER_COLUMN = "routes.number";

    private static final String GET_ALL_QUERY = "SELECT buses.id, buses.model, buses.number, " +
            "buses.driver_id, buses.route_id, buses.ready, " +
            "drivers.first_name, drivers.last_name, routes.number FROM buses " +
            "LEFT OUTER JOIN drivers ON buses.driver_id = drivers.id " +
            "LEFT OUTER JOIN routes ON buses.route_id = routes.id;";

    private static final String GET_BY_ID_QUERY = "SELECT buses.id, buses.model, buses.number, " +
            "buses.driver_id, buses.route_id, buses.ready, " +
            "drivers.first_name, drivers.last_name, routes.number FROM buses " +
            "LEFT OUTER JOIN drivers ON buses.driver_id = drivers.id " +
            "LEFT OUTER JOIN routes ON buses.route_id = routes.id WHERE buses.id = ?;";

    private static final String GET_FOR_ROUTE_QUERY = "SELECT COUNT(*) AS rowcount FROM buses WHERE route_id = ?";

    private static final String ADD_QUERY = "INSERT INTO buses (model, number, ready)" +
            "VALUES (?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM buses WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE buses SET model = ?, number = ?, driver_id = ? " +
            "WHERE id = ?;";

    private static final String SET_READY_QUERY = "UPDATE buses SET ready = true WHERE id = ?;";
    private static final String SET_NOT_READY_QUERY = "UPDATE buses SET ready = false, " +
            "route_id = null, driver_id = null WHERE id = ?";


    @Override
    public List<Bus> getAllBuses() {

        List<Bus> buses = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Bus bus = getBusFromResultSet(resultSet);
                buses.add(bus);
            }

        } catch (SQLException e) {
            LOG.error("Could not get all buses");
        }

        return buses;
    }

    @Override
    public int getCountForRoute(int id) {

        int count = 0;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_FOR_ROUTE_QUERY)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()){
                resultSet.next();
                count = resultSet.getInt("rowcount");
            }
        } catch (SQLException e) {
            LOG.error("Could not get buses count for route: " + id);
        }
        return count;
    }

    @Override
    public Bus getBusById(int id) {

        Bus bus = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                bus = getBusFromResultSet(resultSet);
            }


        } catch (SQLException e) {
            LOG.error("Could not get bus by id: " + id);
        }

        return bus;
    }

    @Override
    public int addBus(Bus bus) {

        int generatedId = -1;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, bus.getModel());
            statement.setString(2, bus.getNumber());
            statement.setBoolean(3, true);
            statement.executeUpdate();

            try(ResultSet resultSet = statement.getGeneratedKeys()){
                if (resultSet.next()) {
                    generatedId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Could not add bus " + bus.getId());
        }
        return generatedId;
    }

    @Override
    public void deleteBus(int id) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not delete bus " + id);
        }
    }

    @Override
    public void updateBus(Bus bus) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, bus.getModel());
            statement.setString(2, bus.getNumber());

            if (bus.getDriver() == null) {
                statement.setNull(3, 0);
            } else {
                statement.setInt(3, bus.getDriver().getId());
            }
            statement.setInt(4, bus.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not update bus " + bus.getId());

        }
    }

    @Override
    public void setReady(int id) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_READY_QUERY)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not set ready bus " + id);
        }
    }

    @Override
    public void setNotReady(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_NOT_READY_QUERY)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not set not ready bus " + id);
        }
    }

    private Bus getBusFromResultSet(ResultSet resultSet) throws SQLException {
        Bus bus = new Bus();
        bus.setId(resultSet.getInt(ID_COLUMN));
        bus.setModel(resultSet.getString(MODEL_COLUMN));
        bus.setNumber(resultSet.getString(BUS_NUMBER_COLUMN));
        bus.setReady(resultSet.getBoolean(READY_COLUMN));

        Integer driverId = resultSet.getInt(DRIVER_ID_COLUMN);
        if (!resultSet.wasNull()) {
            Driver driver = new Driver();
            driver.setId(driverId);
            driver.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
            driver.setLastName(resultSet.getString(LAST_NAME_COLUMN));
            bus.setDriver(driver);
        }

        Integer routeId = resultSet.getInt(ROUTE_ID_COLUMN);
        if (!resultSet.wasNull()) {
            Route route = new Route();
            route.setId(routeId);
            route.setNumber(resultSet.getString(ROUTE_NUMBER_COLUMN));
            bus.setRoute(route);
        }
        return bus;
    }
}
