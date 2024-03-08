package unicap.br.unimpact.api.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse extends DTOResponse {

    private Long id;

    private String senderEmail;

    private String senderName;

    private String subject;

    private String message;

    private Date createdAt;
}
