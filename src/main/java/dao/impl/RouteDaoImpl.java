package dao.impl;

import dao.ConnectionFactory;
import dao.RouteDao;
import entity.Bus;
import entity.Driver;
import entity.Route;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteDaoImpl implements RouteDao {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String ROUTE_ID_COLUMN = "routes.id";
    private static final String BUS_ID_COLUMN = "buses.id";
    private static final String DRIVER_ID_COLUMN = "drivers.id";
    private static final String ROUTE_NUMBER_COLUMN = "routes.number";
    private static final String BUS_NUMBER_COLUMN = "buses.number";
    private static final String MODEL_COLUMN = "buses.model";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String START_POINT_COLUMN = "start_point";
    private static final String END_POINT_COLUMN = "end_point";
    private static final String LENGTH_COLUMN = "length";


    private static final String GET_ALL_QUERY = "SELECT routes.id, routes.number, routes.start_point, " +
            "routes.end_point, routes.length, buses.id, buses.model, buses.number, buses.driver_id, " +
            "drivers.id, drivers.first_name, drivers.last_name FROM routes " +
            "LEFT OUTER JOIN buses ON routes.id = buses.route_id " +
            "LEFT OUTER JOIN drivers ON buses.driver_id = drivers.id;";
    private static final String GET_BY_ID_QUERY = "SELECT routes.id, routes.number, routes.start_point, " +
            "routes.end_point, routes.length, buses.id, buses.model, buses.number, buses.driver_id, " +
            "drivers.id, drivers.first_name, drivers.last_name FROM routes " +
            "LEFT OUTER JOIN buses ON routes.id = buses.route_id " +
            "LEFT OUTER JOIN drivers ON buses.driver_id = drivers.id WHERE routes.id = ?;";
    private static final String ADD_QUERY = "INSERT INTO routes (number, start_point, end_point, length)" +
            "VALUES (?, ?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM routes WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE routes SET number = ?, " +
            "start_point = ?, end_point = ?, length = ? WHERE id = ?;";

    @Override
    public List<Route> getAllRoutes() {

        List<Route> routes = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            routes = getRoutesFromResultSet(resultSet);

        } catch (SQLException e) {
            LOG.error("Could not get all routes");
        }

        return routes;
    }

    @Override
    public Route getRouteById(int id) {

        Route route = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                route = getRoutesFromResultSet(resultSet).get(0);
            }
        } catch (SQLException e) {
            LOG.error("Could not get route by id: " + id);
        }
        return route;
    }

    @Override
    public int addRoute(Route route) {

        int generatedId = -1;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, route.getNumber());
            statement.setString(2, route.getStartPoint());
            statement.setString(3, route.getEndPoint());
            statement.setInt(4, route.getLength());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            LOG.error("Could not add route " + generatedId);
        }
        return generatedId;
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
            statement.setInt(4, route.getLength());
            statement.setInt(5, route.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Could not update route " + route.getId());
        }
    }

    private List<Route> getRoutesFromResultSet(ResultSet resultSet) throws SQLException {

        Map<Integer, Route> routesById = new HashMap<>();

        while (resultSet.next()) {

            int id = resultSet.getInt(ROUTE_ID_COLUMN);
            String routeNumber = resultSet.getString(ROUTE_NUMBER_COLUMN);
            String startPoint = resultSet.getString(START_POINT_COLUMN);
            String endPoint = resultSet.getString(END_POINT_COLUMN);
            int length = resultSet.getInt(LENGTH_COLUMN);

            Route route = routesById.get(id);
            if (route == null) {
                route = new Route();
                route.setId(id);
                route.setNumber(routeNumber);
                route.setStartPoint(startPoint);
                route.setEndPoint(endPoint);
                route.setLength(length);
                routesById.put(id, route);
            }

            Integer busId = resultSet.getInt(BUS_ID_COLUMN);
            if (!resultSet.wasNull()) {
                Bus bus = new Bus();
                bus.setId(busId);
                bus.setModel(resultSet.getString(MODEL_COLUMN));
                bus.setNumber(resultSet.getString(BUS_NUMBER_COLUMN));

                Driver driver = new Driver();
                driver.setId(resultSet.getInt(DRIVER_ID_COLUMN));
                driver.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
                driver.setLastName(resultSet.getString(LAST_NAME_COLUMN));

                bus.setDriver(driver);
                route.addBus(bus);
            }
        }
        return new ArrayList<>(routesById.values());
    }
}
