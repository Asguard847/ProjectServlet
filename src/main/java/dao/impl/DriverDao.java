package dao.impl;

import dao.ConnectionFactory;
import entity.Bus;
import entity.Driver;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDao implements dao.DriverDao {


    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ID_COLUMN = "id";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String PHONE_NUMBER_COLUMN = "phone_number";
    private static final String EMAIL_COLUMN = "email";
    private static final String BUS_ID_COLUMN = "bus_id";

    private static final String GET_ALL_QUERY = "SELECT * FROM drivers;";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM drivers WHERE id = ?;";
    private static final String ADD_QUERY = "INSERT INTO drivers (first_name, last_name, " +
            "phone_number, email, bus_id) VALUES (?, ?, ?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM drivers WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE drivers SET first_name = ?, last_name = ?, " +
            "phone_number = ?, email = ?, bus_id = ? WHERE id = ?;";


    @Override
    public List<Driver> getAllDrivers() {

        List<Driver> drivers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Driver driver = new Driver();
                driver.setId(resultSet.getInt(ID_COLUMN));
                driver.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
                driver.setLastName(resultSet.getString(LAST_NAME_COLUMN));
                driver.setPhoneNumber(resultSet.getString(PHONE_NUMBER_COLUMN));
                driver.setEmail(resultSet.getString(EMAIL_COLUMN));
                driver.setBus_id(resultSet.getInt(BUS_ID_COLUMN));
                drivers.add(driver);
            }

        } catch (SQLException e) {
            LOG.error("Could not get all drivers");
        }
        return drivers;
    }

    @Override
    public Driver getDriverById(int id) {
        Driver driver = null;
        ResultSet resultSet = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();

            driver = new Driver();
            driver.setId(resultSet.getInt(ID_COLUMN));
            driver.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
            driver.setLastName(resultSet.getString(LAST_NAME_COLUMN));
            driver.setPhoneNumber(resultSet.getString(PHONE_NUMBER_COLUMN));
            driver.setEmail(resultSet.getString(EMAIL_COLUMN));
            driver.setBus_id(resultSet.getInt(BUS_ID_COLUMN));

        } catch (SQLException e) {
            LOG.error("Could not get driver by id: " + id);
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOG.error("Could not close ResultSet instance in getDriverById() method");
                }
            }
        }
        return driver;
    }

    @Override
    public void addDriver(Driver driver) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, driver.getFirstName());
            statement.setString(2, driver.getLastName());
            statement.setString(3, driver.getPhoneNumber());
            statement.setString(4, driver.getEmail());
            statement.setInt(5, driver.getBus_id());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not add driver " + driver.getId());
        }
    }

    @Override
    public void deleteDriver(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not delete driver " + id);
        }
    }

    @Override
    public void updateDriver(Driver driver) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, driver.getFirstName());
            statement.setString(2, driver.getLastName());
            statement.setString(3, driver.getPhoneNumber());
            statement.setString(4, driver.getEmail());
            statement.setInt(5, driver.getBus_id());
            statement.setInt(6, driver.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not update driver " + driver.getId());
        }
    }
}
