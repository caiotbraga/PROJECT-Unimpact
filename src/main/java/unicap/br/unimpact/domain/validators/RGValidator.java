package unicap.br.unimpact.domain.validators;

import unicap.br.unimpact.domain.annotations.RG;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RGValidator implements ConstraintValidator<RG, String> {

    @Override
    public void initialize(final RG constraintAnnotation) {
    }

    @Override
    public boolean isValid(String rg, final ConstraintValidatorContext context) {
        return rg == null || "".equals(rg) || rg.length() <= 30;
    }

}
