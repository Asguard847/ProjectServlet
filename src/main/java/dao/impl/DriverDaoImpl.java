package dao.impl;

import dao.ConnectionFactory;
import dao.DriverDao;
import entity.Driver;
import org.apache.log4j.Logger;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDaoImpl implements DriverDao {


    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ID_COLUMN = "id";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String PHONE_NUMBER_COLUMN = "phone_number";
    private static final String EMAIL_COLUMN = "email";
    private static final String READY_COLUMN = "ready";
    private static final String FREE_COLUMN = "free";

    private static final String GET_ALL_QUERY = "SELECT * FROM drivers;";
    private static final String GET_FREE_QUERY = "SELECT * FROM drivers WHERE ready = true AND free = true;";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM drivers WHERE id = ?;";
    private static final String ADD_QUERY = "INSERT INTO drivers (first_name, last_name, " +
            "phone_number, email, ready, free) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM drivers WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE drivers SET first_name = ?, last_name = ?, " +
            "phone_number = ?, email = ? WHERE id = ?;";
    private static final String SET_READY_QUERY = "UPDATE drivers SET ready = true, free = true WHERE id = ?";
    private static final String SET_NOT_READY_QUERY = "UPDATE drivers SET ready = false, free = true WHERE id = ?";
    private static final String SET_FREE_QUERY = "UPDATE drivers SET free = true WHERE id = ?";
    private static final String SET_NOT_FREE_QUERY = "UPDATE drivers SET free = false WHERE id = ?";

    private static final String REMOVE_FROM_BUS_QUERY = "UPDATE buses SET driver_id = null, route_id = null WHERE driver_id = ?;";

    private static final String ADD_USER_QUERY = "INSERT INTO users (username, password, authority, enabled) VALUES (?, ?, ?, ?);";

    private static final String USER_AUTHORITY = "ROLE_USER";

    private BCryptPasswordEncoder encoder;

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
                driver.setReady((resultSet.getBoolean(READY_COLUMN)));
                driver.setFree((resultSet.getBoolean(FREE_COLUMN)));
                drivers.add(driver);
            }

        } catch (SQLException e) {
            LOG.error("Could not get all drivers");
        }
        return drivers;
    }

    @Override
    public List<Driver> getFreeDrivers() {

        List<Driver> drivers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_FREE_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Driver driver = new Driver();
                driver.setId(resultSet.getInt(ID_COLUMN));
                driver.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
                driver.setLastName(resultSet.getString(LAST_NAME_COLUMN));
                driver.setPhoneNumber(resultSet.getString(PHONE_NUMBER_COLUMN));
                driver.setEmail(resultSet.getString(EMAIL_COLUMN));
                driver.setReady(true);
                driver.setFree(true);
                drivers.add(driver);
            }

        } catch (SQLException e) {
            LOG.error("Could not get all free drivers");
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
            driver.setReady((resultSet.getBoolean(READY_COLUMN)));
            driver.setFree((resultSet.getBoolean(FREE_COLUMN)));

        } catch (SQLException e) {
            LOG.error("Could not get driver by id: " + id);
        } finally {
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
    public int addDriver(Driver driver) {

        int generatedId = -1;
        ResultSet resultSet = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_QUERY,
                     Statement.RETURN_GENERATED_KEYS);
             PreparedStatement userStatement = connection.prepareStatement(ADD_USER_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false);

            statement.setString(1, driver.getFirstName());
            statement.setString(2, driver.getLastName());
            statement.setString(3, driver.getPhoneNumber());
            statement.setString(4, driver.getEmail());
            statement.setBoolean(5, true);
            statement.setBoolean(6, true);
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }

            String password = generatePassword();
            userStatement.setString(1, driver.getEmail());
            userStatement.setString(2, encodePassword(password));
            userStatement.setString(3, USER_AUTHORITY);
            userStatement.setBoolean(4, true);
            userStatement.executeUpdate();

            connection.commit();

            //TODO Send Email to User with credentials

        } catch (SQLException e) {
            LOG.error("Could not add driver " + driver.getId());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOG.error("Could not close ResultSet instance in addDriver() method");
                }
            }
        }
        return generatedId;
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
            statement.setInt(5, driver.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not update driver " + driver.getId());
        }
    }

    @Override
    public void setReady(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_READY_QUERY)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not set ready driver " + id);
        }
    }

    @Override
    public void setNotReady(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_NOT_READY_QUERY);
             PreparedStatement busStatement = connection.prepareStatement(REMOVE_FROM_BUS_QUERY)){

            connection.setAutoCommit(false);

            statement.setInt(1, id);
            statement.executeUpdate();

            busStatement.setInt(1, id);
            busStatement.executeUpdate();

            connection.commit();

        }catch (SQLException e) {
            LOG.error("Could not set not ready driver " + id);
        }
    }

    @Override
    public void setFree(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_FREE_QUERY)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not set free driver " + id);
        }
    }

    @Override
    public void setNotFree(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_NOT_FREE_QUERY)){

            statement.setInt(1, id);
            statement.executeUpdate();

        }catch (SQLException e) {
            LOG.error("Could not set not free driver " + id);
        }
    }

    private String generatePassword(){

        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(3);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(3);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(3);

        String password = gen.generatePassword(9, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }

    private String encodePassword(String password){
        if(encoder == null){
            encoder = new BCryptPasswordEncoder();
        }
        return encoder.encode(password);
    }
}
