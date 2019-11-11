package dao.impl;

import dao.ConnectionFactory;
import dao.UserDao;
import entity.User;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String PASSWORD_COLUMN = "password";
    private static final String ENABLED_COLUMN = "enabled";
    private static final String AUTHORITY_COLUMN = "authority";

    private static final String USER_AUTHORITY = "ROLE_USER";

    private static final String GET_USER_QUERY = "SELECT * FROM users  WHERE username = ?;";
    private static final String ADD_USER_QUERY = "INSERT INTO users VALUES (?, ?, ?, ?);";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?;";
    private static final String UPDATE_USER = "UPDATE users " +
            "SET password = ?, authority = ?, enabled = ? " +
            "WHERE username = ?;";

    private BCryptPasswordEncoder encoder;

    public User getUserByUsername(String username) {

        User user = null;
        ResultSet resultSet = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER_QUERY)) {

            statement.setString(1, username);
            resultSet = statement.executeQuery();
            resultSet.next();

            user = new User();
            user.setUsername(username);
            user.setPassword(resultSet.getString(PASSWORD_COLUMN));
            user.setEnabled(resultSet.getBoolean(ENABLED_COLUMN));
            user.setAuthority(resultSet.getString(AUTHORITY_COLUMN));

        } catch (SQLException e) {
            LOG.error("Could not get user");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOG.error("Could not close ResultSet instance in getUserByUsername() method");
                }
            }
        }
        return user;
    }

    @Override
    public void addUser(User user) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_USER_QUERY)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, encodePassword(user.getPassword()));
            statement.setString(3, USER_AUTHORITY);
            statement.setBoolean(4, true);

            statement.execute();

        } catch (SQLException e) {
            LOG.error("Could not add user");
        }
    }

    @Override
    public void deleteUser(String username) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_QUERY)) {

            statement.setString(1, username);
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Could not delete user");
        }
    }

    @Override
    public void updateUser(User user) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {

            statement.setString(1, encodePassword(user.getPassword()));
            statement.setString(2, USER_AUTHORITY);
            statement.setBoolean(3, user.isEnabled());
            statement.setString(4, user.getUsername());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not delete user");
        }
    }

    private String encodePassword(String password){
        if(encoder == null){
            encoder = new BCryptPasswordEncoder();
        }
        return encoder.encode(password);
    }
}
