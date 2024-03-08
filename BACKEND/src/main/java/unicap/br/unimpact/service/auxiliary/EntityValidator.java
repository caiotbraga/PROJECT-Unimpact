package unicap.br.unimpact.service.auxiliary;

import unicap.br.unimpact.api.dtos.response.ErrorResponse;
import unicap.br.unimpact.service.exceptions.business.InvalidFieldException;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;

public class EntityValidator {

    public static void validate(Object obj) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        List<ErrorResponse> errors = validator.validate(obj).stream()
                .map(r -> new ErrorResponse(r.getPropertyPath().toString(), r.getMessage())).toList();
        if (!errors.isEmpty()) throw new InvalidFieldException(errors.get(0));
    }
}
