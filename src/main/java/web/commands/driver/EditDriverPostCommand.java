package web.commands.driver;

import entity.Driver;
import org.apache.log4j.Logger;
import service.DriverService;
import service.impl.DriverServiceImpl;
import web.ImageUtils;
import web.Page;
import web.ValidationUtils;
import web.commands.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.lang.invoke.MethodHandles;

import static web.Constants.*;

public class EditDriverPostCommand implements Command {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());

    private static final String F_NAME_MSG = "First name is incorrect";
    private static final String L_NAME_MSG = "Last name is incorrect";
    private static final String PHONE_MSG = "Phone number is incorrect";
    private static final String EMAIL_MSG = "Email is incorrect";


    @Override
    public Page perform(HttpServletRequest request, ServletContext ctx) {

        boolean inputCorrect = true;

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        int id = (Integer)request.getAttribute("id");

        if (!ValidationUtils.validateFirstName(firstName)) {
            request.setAttribute("fNameVal", F_NAME_MSG);
            inputCorrect = false;
        }

        if (!ValidationUtils.validateLastName(lastName)) {
            request.setAttribute("lNameVal", L_NAME_MSG);
            inputCorrect = false;
        }

        if (!ValidationUtils.validatePhone(phone)) {
            request.setAttribute("phoneVal", PHONE_MSG);
            inputCorrect = false;
        }

        if (!ValidationUtils.validateEmail(email)) {
            request.setAttribute("emailVal", EMAIL_MSG);
            inputCorrect = false;
        }

        Driver driver = new Driver();
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setPhoneNumber(phone);
        driver.setEmail(email);
        driver.setId(id);

        if (!inputCorrect) {

            request.setAttribute("driver", driver);
            return new Page(PREFIX + "editDriver" + POSTFIX);

        } else {
            DriverService driverService = (DriverServiceImpl) ctx.getAttribute(DRIVER_SERVICE);
            driverService.updateDriver(driver);

            ImageUtils.saveImage(request, id);
            LOG.info("Driver updated: " + id);
            return new Page("/app/admin/drivers", true);
        }
    }
}

