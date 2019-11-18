package dao.impl;

import dao.DriverDao;
import entity.Driver;
import org.junit.Test;

import static org.junit.Assert.*;

public class DriverDaoImplTest {

    @Test
    public void testAddDriver(){
        DriverDao dao = new DriverDaoImpl();
        Driver driver = new Driver();
        driver.setFirstName("aaa");
        driver.setLastName("bbb");
        driver.setEmail("polniyplatsap@gmail.com");
        driver.setPhoneNumber("+380637891786");
        int i = dao.addDriver(driver);
        System.out.println(i);
        assertTrue(i>0);
    }

}