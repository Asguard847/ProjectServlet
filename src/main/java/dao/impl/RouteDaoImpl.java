package dao.impl;

import dao.ConnectionFactory;
import dao.RouteDao;
import entity.Route;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ID_COLUMN = "id";
    private static final String NUMBER_COLUMN = "number";
    private static final String START_POINT_COLUMN = "start_point";
    private static final String END_POINT_COLUMN = "end_point";

    private static final String GET_ALL_QUERY = "SELECT * FROM routes;";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM routes WHERE id = ?;";
    private static final String ADD_QUERY = "INSERT INTO routes (number, start_point, end_point)" +
            "VALUES (?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM routes WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE routes SET number = ?, " +
            "start_point = ?, end_point = ? WHERE id = ?;";

    @Override
    public List<Route> getAllRoutes() {

        List<Route> routes = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getInt(ID_COLUMN));
                route.setNumber(resultSet.getString(NUMBER_COLUMN));
                route.setStartPoint(resultSet.getString(START_POINT_COLUMN));
                route.setEndPoint(resultSet.getString(END_POINT_COLUMN));
                routes.add(route);
            }

        } catch (SQLException e) {
            LOG.error("Could not get all routes");
        }

        return routes;
    }

    @Override
    public Route getRouteById(int id) {

        Route route = null;
        ResultSet resultSet = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();

            route = new Route();
            route.setId(resultSet.getInt(ID_COLUMN));
            route.setNumber(resultSet.getString(NUMBER_COLUMN));
            route.setStartPoint(resultSet.getString(START_POINT_COLUMN));
            route.setEndPoint(resultSet.getString(END_POINT_COLUMN));

        } catch (SQLException e) {
            LOG.error("Could not get route by id: " + id);
        }finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOG.error("Could not close ResultSet instance in getRouteById() method");
                }
            }
        }
        return route;
    }

    @Override
    public void addRoute(Route route) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, route.getNumber());
            statement.setString(2, route.getStartPoint());
            statement.setString(3, route.getEndPoint());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not add route " + route.getId());
        }
    }

    @Override
    public void deleteRoute(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not delete route " + id);
        }
    }

    @Override
    public void updateRoute(Route route) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, route.getNumber());
            statement.setString(2, route.getStartPoint());
            statement.setString(3, route.getEndPoint());
            statement.setInt(4, route.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not update route " + route.getId());
        }
    }
}
