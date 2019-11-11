package dao.impl;

import dao.BusDao;
import dao.ConnectionFactory;
import entity.Bus;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDaoImpl implements BusDao {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ID_COLUMN = "id";
    private static final String MODEL_COLUMN = "model";
    private static final String NUMBER_COLUMN = "number";
    private static final String ROUTE_ID_COLUMN = "route_id";
    private static final String DRIVER_ID_COLUMN = "driver_id";

    private static final String GET_ALL_QUERY = "SELECT * FROM buses;";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM buses WHERE id = ?;";
    private static final String ADD_QUERY = "INSERT INTO buses (model, number, route_id, driver_id)" +
            "VALUES (?, ?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM buses WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE buses SET model = ?, number = ?, " +
            "route_id = ?, driver_id = ? WHERE id = ?;";

    @Override
    public List<Bus> getAllBuses() {

        List<Bus> buses = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Bus bus = new Bus();
                bus.setId(resultSet.getInt(ID_COLUMN));
                bus.setModel(resultSet.getString(MODEL_COLUMN));
                bus.setNumber(resultSet.getString(NUMBER_COLUMN));
                bus.setRoute_id(resultSet.getInt(ROUTE_ID_COLUMN));
                bus.setDriver_id(resultSet.getInt(DRIVER_ID_COLUMN));
                buses.add(bus);
            }

        } catch (SQLException e) {
            LOG.error("Could not get all buses");
        }

        return buses;
    }

    @Override
    public Bus getBusById(int id) {

        Bus bus = null;
        ResultSet resultSet = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();

            bus = new Bus();
            bus.setId(resultSet.getInt(ID_COLUMN));
            bus.setModel(resultSet.getString(MODEL_COLUMN));
            bus.setNumber(resultSet.getString(NUMBER_COLUMN));
            bus.setRoute_id(resultSet.getInt(ROUTE_ID_COLUMN));
            bus.setDriver_id(resultSet.getInt(DRIVER_ID_COLUMN));

        } catch (SQLException e) {
            LOG.error("Could not get bus by id: " + id);
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOG.error("Could not close ResultSet instance in getBusById() method");
                }
            }
        }
        return bus;
    }

    @Override
    public void addBus(Bus bus) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, bus.getModel());
            statement.setString(2, bus.getNumber());
            statement.setInt(3, bus.getRoute_id());
            statement.setInt(4, bus.getDriver_id());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not add bus " + bus.getId());
        }
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
            statement.setInt(3, bus.getRoute_id());
            statement.setInt(4, bus.getDriver_id());
            statement.setInt(5, bus.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not update bus " + bus.getId());

        }
    }
}
