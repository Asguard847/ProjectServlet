package web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static boolean validateFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\p{L}+");
        Matcher matcher = pattern.matcher(firstName);
        return matcher.matches();
    }

   public static boolean validateLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\p{L}+");
        Matcher matcher = pattern.matcher(lastName);
        return matcher.matches();
    }

    public static boolean validatePhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\+\\d{12}");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validateBusNumber(String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean validateBusModel(String model) {
        if (model == null || model.isEmpty()) {
            return false;
        }
        return true;
    }
}
