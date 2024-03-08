package unicap.br.unimpact.service.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.*;
import unicap.br.unimpact.domain.enums.NotificationStatusEnum;
import unicap.br.unimpact.repository.NotificationRepository;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class NotificationRemover {

    @Autowired
    NotificationRepository repository;


    public List<Notification> deleteAll(List<Long> notificationsId) {
        List<Notification> notifications = new ArrayList<>();

        for (Long id : notificationsId) {
            notifications.add(repository.getById(id));
        }

        this.repository.deleteAll(notifications);

        return notifications;
    }

    public List<Notification> deleteAllReceivedByEmail(String email) {
        List<Notification> notifications = repository.getAllByRecipientEmailAndIsVisible(email, true);
        notifications.forEach(n -> this.deleteNotification(n.getId(), n.getStatus()));
        return notifications;
    }


    public List<Notification> deleteAllSentByEmail(String email) {
        List<Notification> notifications = repository.getAllBySenderEmailAndIsVisible(email, true);
        notifications.forEach(n -> this.deleteNotification(n.getId(), n.getStatus()));
        return notifications;
    }

    public Notification deleteNotification(Long notificationId, NotificationStatusEnum actualStatus) {
        Optional<Notification> notification = repository.findById(notificationId);

        if (notification.isEmpty()) {
            throw new NotFoundException(Notification.class);
        }

        notification.get().setIsVisible(false);
        notification.get().setStatus(actualStatus);
        repository.save(notification.get());

        return notification.get();
    }

}
