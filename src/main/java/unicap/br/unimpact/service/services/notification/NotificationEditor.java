package unicap.br.unimpact.service.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Notification;
import unicap.br.unimpact.domain.enums.NotificationStatusEnum;
import unicap.br.unimpact.repository.NotificationRepository;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class NotificationEditor {

    @Autowired
    NotificationRepository repository;


    public Notification setViwed(Long notificationId) {
        Notification notification = repository.getById(notificationId);
        notification.setStatus(NotificationStatusEnum.VIEWED);
        this.repository.save(notification);
        return notification;
    }

}
