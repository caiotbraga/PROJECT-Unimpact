package unicap.br.unimpact.domain.validators;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    public boolean isValid(String password) {
        boolean result;
        if (password == null || "".equals(password)) {
            result = true;
        } else {
            Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,30}$");
            Matcher matcher = pattern.matcher(password);
            result = matcher.find();
        }
        return result;
    }

}
