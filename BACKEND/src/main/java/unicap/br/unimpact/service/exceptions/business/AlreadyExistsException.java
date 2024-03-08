package unicap.br.unimpact.service.exceptions.business;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import unicap.br.unimpact.service.exceptions.BaseRuntimeException;

import java.util.Map;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends BaseRuntimeException {
    private static final String KEY = "already.exists";

    public AlreadyExistsException(String value) {
        super(Map.of("value", value));
    }

    public AlreadyExistsException(Class<?> typeClass) {
        super(Map.of("value", typeClass.getSimpleName()));
    }

    @Override
    public String getExceptionKey() {
        return KEY;
    }
}
