package unicap.br.unimpact.service.exceptions.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import unicap.br.unimpact.api.dtos.response.ErrorResponse;
import unicap.br.unimpact.service.exceptions.BaseRuntimeException;

import java.util.Map;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DisallowedDeletionException extends BaseRuntimeException {
    private static final String KEY = "disallowed.deletion";

    public DisallowedDeletionException(String value) {
        super(Map.of("value", value));
    }

    public DisallowedDeletionException(Class<?> typeClass) {
        super(Map.of("value", typeClass.getSimpleName()));
    }

    public DisallowedDeletionException(ErrorResponse errorResponse) {
        super(Map.of("value", errorResponse.getKey() + errorResponse.getError()));
    }

    @Override
    public String getExceptionKey() {
        return KEY;
    }
}
