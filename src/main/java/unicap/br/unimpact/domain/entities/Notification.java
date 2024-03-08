package unicap.br.unimpact.domain.entities;

import lombok.*;
import unicap.br.unimpact.domain.enums.NotificationStatusEnum;
import unicap.br.unimpact.domain.enums.NotificationTypeEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationTypeEnum type;

    private String senderEmail;

    private String senderName;

    private String recipientEmail;

    private String recipientName;

    private Boolean isVisible;

    private String extraValue;

    @Enumerated(EnumType.STRING)
    private NotificationStatusEnum status;


    public Notification(String message, NotificationTypeEnum type, String senderEmail, String senderName,
                        String recipientEmail, String recipientName, Boolean isVisible) {
        this.message = message;
        this.type = type;
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.recipientEmail = recipientEmail;
        this.recipientName = recipientName;
        this.isVisible = isVisible;
        this.status = NotificationStatusEnum.PENDANT;
    }


    public Notification(String message, NotificationTypeEnum type, String senderEmail, String senderName,
                        String recipientEmail, String recipientName, Boolean isVisible, String extraValue) {
        this(message, type, senderEmail, senderName, recipientEmail, recipientName, isVisible);
        this.extraValue = extraValue;
    }
}
