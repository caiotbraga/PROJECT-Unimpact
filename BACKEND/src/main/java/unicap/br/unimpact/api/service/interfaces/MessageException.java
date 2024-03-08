package unicap.br.unimpact.service.interfaces;

import java.util.Map;

public interface MessageException {

    String getExceptionKey();

    Map<String, Object> getMapDetails();
}
