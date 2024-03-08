package unicap.br.unimpact.domain.validators;

import unicap.br.unimpact.domain.annotations.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(final Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, final ConstraintValidatorContext context) {
        boolean result;
        if (email == null || "".equals(email)) {
            result = true;
        } else {
            Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            result = matcher.find();
        }
        return result;
    }

}
