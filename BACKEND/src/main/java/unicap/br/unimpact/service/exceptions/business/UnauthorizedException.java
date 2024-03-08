package unicap.br.unimpact.service.exceptions.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import unicap.br.unimpact.api.dtos.response.ErrorResponse;
import unicap.br.unimpact.service.exceptions.BaseRuntimeException;

import java.util.Map;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnauthorizedException extends BaseRuntimeException {
    private static final String KEY = "unauthorized";

    public UnauthorizedException(String value) {
        super(Map.of("value", value));
    }

    public UnauthorizedException(Class<?> typeClass) {
        super(Map.of("value", typeClass.getSimpleName()));
    }

    public UnauthorizedException(ErrorResponse errorResponse) {
        super(Map.of("value", errorResponse.getKey() + errorResponse.getError()));
    }

    @Override
    public String getExceptionKey() {
        return KEY;
    }
}
