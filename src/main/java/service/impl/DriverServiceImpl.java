package service.impl;

import dao.DriverDao;
import dao.impl.DriverDaoImpl;
import entity.Driver;
import org.apache.log4j.Logger;
import service.DriverService;
import web.ImageUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DriverServiceImpl implements DriverService {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String F_NAME_MSG = "First name is incorrect";
    private static final String L_NAME_MSG = "Last name is incorrect";
    private static final String PHONE_MSG = "Phone number is incorrect";
    private static final String EMAIL_MSG = "Email is incorrect";

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String ID = "id";

    private static final String NAME_PATTERN = "\\p{L}+";
    private static final String PHONE_PATTERN = "\\+\\d{12}";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    private static DriverDao driverDao;

    static {
        driverDao = new DriverDaoImpl();
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverDao.getAllDrivers();
    }

    @Override
    public List<Driver> getFreeDrivers() {
        return driverDao.getFreeDrivers();
    }

    @Override
    public Driver getDriverById(int id) {
        return driverDao.getDriverById(id);
    }

    @Override
    public Driver getDriverByEmail(String email) {
        return driverDao.getDriverByEmail(email);
    }

    @Override
    public int addDriver(Driver driver) {

        int driverId = driverDao.addDriver(driver);
        LOG.info("Driver persisted: " + driverId);
        return driverId;
    }

    @Override
    public void deleteDriver(int id) {
        driverDao.deleteDriver(id);
        LOG.info("Driver deleted: " + id);
    }

    @Override
    public void updateDriver(Driver driver, int id) {

        driver.setId(id);
        driverDao.updateDriver(driver);
    }

    @Override
    public void setReady(int id, boolean ready) {
        driverDao.setReady(id, ready);
    }

    @Override
    public void setFree(int id, boolean free) {
        driverDao.setFree(id, free);
    }

    @Override
    public boolean validateDriverInput(HttpServletRequest request) {

        Driver driver = getDriverFromRequest(request);

        boolean driverInputIncorrect = false;

        if (validateFirstName(driver.getFirstName())) {
            request.setAttribute("fNameVal", F_NAME_MSG);
            driverInputIncorrect = true;
        }

        if (validateLastName(driver.getLastName())) {
            request.setAttribute("lNameVal", L_NAME_MSG);
            driverInputIncorrect = true;
        }

        if (validatePhone(driver.getPhoneNumber())) {
            request.setAttribute("phoneVal", PHONE_MSG);
            driverInputIncorrect = true;
        }

        if (validateEmail(driver.getEmail())) {
            request.setAttribute("emailVal", EMAIL_MSG);
            driverInputIncorrect = true;
        }

        Object id = request.getAttribute(ID);
        if(id!=null){
            driver.setId((Integer)id);
        }

        request.setAttribute("driver", driver);

        return driverInputIncorrect;
    }

    private boolean validateFirstName(String firstName) {
        if (nullOrEmpty(firstName)) {
            return true;
        }
        return !matchesPattern(firstName, NAME_PATTERN);
    }

    private  boolean validateLastName(String lastName) {
        if (nullOrEmpty(lastName)) {
            return true;
        }
        return !matchesPattern(lastName, NAME_PATTERN);
    }

    private  boolean validatePhone(String phone) {
        if (nullOrEmpty(phone)) {
            return true;
        }
        return !matchesPattern(phone, PHONE_PATTERN);
    }

    private  boolean validateEmail(String email) {
        if (nullOrEmpty(email)) {
            return true;
        }
        return !matchesPattern(email, EMAIL_PATTERN);
    }

    public static Driver getDriverFromRequest(HttpServletRequest request){

        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String phone = request.getParameter(PHONE);
        String email = request.getParameter(EMAIL);

        Driver driver = new Driver();
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setPhoneNumber(phone);
        driver.setEmail(email);

        return driver;
    }

    private boolean nullOrEmpty(String val){
        return val == null || val.isEmpty();
    }

    private boolean matchesPattern(String val, String patternString){
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(val);
        return matcher.matches();
    }
}

