package dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class for getting {@link Connection} instance.
 * For connection pooling is used Apache Commons DBCP component,
 * {@link BasicDataSource} implementation of
 * {@link DataSource} interface.
 */

public class ConnectionFactory {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String DB_PROPERTIES = "/db.properties";
    private static final String DB_URL = "DB_URL";
    private static final String DB_USERNAME = "DB_USERNAME";
    private static final String DB_PASSWORD = "DB_PASSWORD";

    private static BasicDataSource basicDataSource;

    static {

        Properties properties = new Properties();

        try {
            properties.load(ConnectionFactory.class.getResourceAsStream(DB_PROPERTIES));
        } catch (IOException e) {
            LOG.error("Could not read properties from file");
        }
        LOG.info("Got DB properties");

        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(properties.getProperty(DB_URL));
        basicDataSource.setUsername(properties.getProperty(DB_USERNAME));
        basicDataSource.setPassword(properties.getProperty(DB_PASSWORD));
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(10);
        basicDataSource.setMaxOpenPreparedStatements(100);

        LOG.info("BasicDataSource configured");

    }

    public static Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return basicDataSource;
    }
}
