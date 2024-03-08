package unicap.br.unimpact.api.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unicap.br.unimpact.api.dtos.response.DTOResponse;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequest {

    private String subject;

    private String message;
}
