package unicap.br.unimpact.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private String key;

    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }
}
