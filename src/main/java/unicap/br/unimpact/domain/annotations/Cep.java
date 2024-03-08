package unicap.br.unimpact.domain.annotations;

import unicap.br.unimpact.domain.validators.CepValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = CepValidator.class)
public @interface Cep {

    Class<?>[] groups() default {};

    String message() default "";

    Class<? extends Payload>[] payload() default {};

}
