package unicap.br.unimpact.service.exceptions;

import unicap.br.unimpact.service.interfaces.MessageException;

import java.util.Map;

public abstract class BaseRuntimeException extends RuntimeException implements MessageException {

    private final Map<String, Object> mapDetails;

    public BaseRuntimeException(final Map<String, Object> mapDetails) {
        this.mapDetails = mapDetails;
    }

    public abstract String getExceptionKey();

    public Map<String, Object> getMapDetails() {
        return this.mapDetails;
    }
}
