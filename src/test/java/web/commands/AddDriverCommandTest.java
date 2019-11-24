package web.commands;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class AddDriverCommandTest {

    @Test
    public void testUnicodeRegEx(){
        Pattern pattern = Pattern.compile("\\p{L}+");
        Matcher matcher = pattern.matcher("Руслан");
        assertTrue(matcher.matches());
    }

    @Test
    public void testPhoneValidation(){
        Pattern pattern = Pattern.compile("\\+\\d{12}");
        Matcher matcher = pattern.matcher("+380637891786");
        assertTrue(matcher.matches());
    }
}