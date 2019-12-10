package dao;

import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;


import java.sql.SQLException;

import static org.junit.Assert.*;

public class ConnectionFactoryTest {

    @Test
    public void testDbConnectionGet() throws SQLException {
        assertNotNull(ConnectionFactory.getConnection());
    }

    @Test
    public void testPassword(){
        System.out.println(BCrypt.hashpw("admin", BCrypt.gensalt()));


    }
}