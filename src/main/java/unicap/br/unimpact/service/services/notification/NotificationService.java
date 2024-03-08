package unicap.br.unimpact.service.services.notification;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.response.NotificationResponse;
import unicap.br.unimpact.domain.entities.Notification;
import unicap.br.unimpact.repository.NotificationRepository;
import unicap.br.unimpact.service.exceptions.business.InvalidFieldException;
import unicap.br.unimpact.service.interfaces.GenericCRUD;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class NotificationService extends GenericCRUD<Notification, NotificationResponse> {

    @Autowired
    NotificationRepository repository;

    @Autowired
    NotificationRegister register;

    @Autowired
    NotificationRemover remover;

    @Autowired
    NotificationEditor editor;

    @Autowired
    NotificationSearcher searcher;

    @PostConstruct
    public void init() {
        this.init(Notification.class, NotificationResponse.class,
                this.repository, null);
    }


    public void validate(Long notificationId, String notificationType) {
        Notification notification = repository.getById(notificationId);
        if (!notification.getType().name().equals(notificationType)) {
            throw new InvalidFieldException("notificationType");
        }
    }


    public List<Notification> getAllByRecipient(String email) {
        return searcher.getAllByRecipient(email);
    }


    public List<Notification> getAllBySender(String email) {
        return searcher.getAllBySender(email);
    }


    public Notification setViwed(Long notificationId) {
        return editor.setViwed(notificationId);
    }


    public List<Notification> deleteAll(List<Long> notificationsId) {
        return remover.deleteAll(notificationsId);
    }

    public List<Notification> deleteAllReceivedByEmail(String email) {
        return remover.deleteAllReceivedByEmail(email);
    }


    public List<Notification> deleteAllSentByEmail(String email) {
        return remover.deleteAllSentByEmail(email);
    }

    public void removalNotice(String senderEmail, String recipientEmail) {
        register.removalNotice(senderEmail, recipientEmail);
    }

    public void removalNotice(String senderEmail, String recipientEmail, String groupName) {
        register.removalNotice(senderEmail, recipientEmail, groupName);
    }

    public void leaveNotice(String senderEmail, String recipientEmail, String groupName) {
        register.leaveNotice(senderEmail, recipientEmail, groupName);
    }

    public List<Notification> inviteRepresentatives(String senderEmail, List<String> recipients) {
        return register.inviteRepresentatives(senderEmail, recipients);
    }

    public void acceptInviteRepresentative(Long notificationId) {
        register.acceptInviteRepresentative(notificationId);
    }

    public void declineInviteRepresentative(Long notificationId) {
        register.declineInviteRepresentative(notificationId);
    }

    public List<Notification> inviteGroupMembers(String senderEmail, Long groupId, List<String> recipients) {
        return register.inviteGroupMembers(senderEmail, groupId, recipients);
    }

    public void acceptInviteGroup(Long notificationId) {
        register.acceptInviteGroup(notificationId);
    }

    public void declineInviteGroup(Long notificationId) {
        register.declineInviteGroup(notificationId);
    }
}
