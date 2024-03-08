package unicap.br.unimpact.service.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Notification;
import unicap.br.unimpact.domain.enums.NotificationStatusEnum;
import unicap.br.unimpact.repository.NotificationRepository;

import java.util.List;

@Component
public class NotificationSearcher {

    @Autowired
    NotificationRepository repository;


    public List<Notification> getAllByRecipient(String email) {
        return repository.getAllByRecipientEmailAndIsVisible(email, true);
    }


    public List<Notification> getAllBySender(String email) {
        return repository.getAllBySenderEmailAndIsVisible(email, true);
    }

}
