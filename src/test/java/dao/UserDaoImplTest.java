package dao;

import dao.impl.UserDaoImpl;
import entity.User;
import org.junit.Test;


import static org.junit.Assert.*;

public class UserDaoImplTest {

    @Test
    public void getUserByUsername() {

        UserDaoImpl dao = new UserDaoImpl();
        User user = dao.getUserByUsername("admin");
        assertNotNull(user);
        assertEquals("ROLE_ADMIN", user.getAuthority());
        assertEquals(true, user.isEnabled());

    }
}