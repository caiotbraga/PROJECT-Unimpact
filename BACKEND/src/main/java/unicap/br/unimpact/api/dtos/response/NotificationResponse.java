package unicap.br.unimpact.api.dtos.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import unicap.br.unimpact.domain.enums.NotificationStatusEnum;
import unicap.br.unimpact.domain.enums.NotificationTypeEnum;

import java.util.Date;


@Data
@NoArgsConstructor
public class NotificationResponse extends DTOResponse {

    private Long id;

    private String message;

    private NotificationTypeEnum type;

    private String senderEmail;

    private String senderName;

    private String recipientEmail;

    private String recipientName;

    private NotificationStatusEnum status;

    private Date createdAt;
}
