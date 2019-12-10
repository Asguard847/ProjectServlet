package dao;

import dao.impl.UserDaoImpl;
import entity.User;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

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

    @Test
    public void testPassword(){
        System.out.println(BCrypt.hashpw("admin", BCrypt.gensalt()));
    }
}