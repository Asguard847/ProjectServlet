package dao;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class ConnectionFactoryTest {

    @Test
    public void testDbConnectionGet() throws SQLException {
        assertNotNull(ConnectionFactory.getConnection());
    }

    @Test
    public void testPassword(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));
    }
}