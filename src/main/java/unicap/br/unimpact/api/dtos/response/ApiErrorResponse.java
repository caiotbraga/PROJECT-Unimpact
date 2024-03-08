package unicap.br.unimpact.api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
public class ApiErrorResponse {

    private LocalDate timestamp;

    private Integer status;

    private String code;

    private Set<ErrorResponse> errors;
}
