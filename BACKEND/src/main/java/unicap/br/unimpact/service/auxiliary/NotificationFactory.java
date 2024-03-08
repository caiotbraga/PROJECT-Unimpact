package unicap.br.unimpact.service.auxiliary;

import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Notification;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.NotificationMessageEnum;
import unicap.br.unimpact.domain.enums.NotificationTypeEnum;

@Component
public class NotificationFactory {

    private String findMessageByKey(NotificationMessageEnum key) {
        return NotificationMessageEnum.getMessage(key);
    }


    private Notification createNotification(User sender, User recipient, String message, NotificationTypeEnum type) {
        return new Notification(message,
                type,
                sender.getEmail(),
                sender.getName(),
                recipient.getEmail(),
                recipient.getName(),
                true
        );
    }


    public Notification createRemovalNotice(User sender, User recipient) {
        return createNotification(sender, recipient,
                String.format(findMessageByKey(NotificationMessageEnum.REMOVAL_REPRESENTATIVE),
                        sender.getName()), NotificationTypeEnum.REMOVAL_NOTICE);
    }


    public Notification createRemovalNotice(User sender, User recipient, String groupName) {
        return createNotification(sender, recipient,
                String.format(findMessageByKey(NotificationMessageEnum.REMOVAL_GROUP),
                        sender.getName(), groupName), NotificationTypeEnum.REMOVAL_NOTICE);
    }


    public Notification createLeaveNotice(User sender, User recipient, String groupName) {
        return createNotification(sender, recipient,
                String.format(findMessageByKey(NotificationMessageEnum.LEAVE_GROUP),
                        sender.getName(), groupName), NotificationTypeEnum.REMOVAL_NOTICE);
    }


    public Notification createInviteRepresentative(User sender, User recipient) {
        return createNotification(sender, recipient,
                String.format(findMessageByKey(NotificationMessageEnum.INVITE_REPRESENTATIVE),
                        sender.getName()), NotificationTypeEnum.INVITE_REPRESENTATIVE);
    }


    public Notification createInviteRepresentativeAccepted(User sender, User recipient) {
        return createNotification(sender, recipient,
                String.format(findMessageByKey(NotificationMessageEnum.ACCEPT_INVITE_REPRESENTATIVE),
                        sender.getName()), NotificationTypeEnum.ACCEPT_INVITE_REPRESENTATIVE);
    }


    public Notification createInviteRepresentativeDeclined(User sender, User recipient) {
        return createNotification(sender, recipient,
                String.format(findMessageByKey(NotificationMessageEnum.DECLINE_INVITE_REPRESENTATIVE),
                        sender.getName()), NotificationTypeEnum.DECLINE_INVITE_REPRESENTATIVE);
    }


    public Notification createInviteGroup(User sender, User recipient, String groupName) {
        Notification notification = createNotification(sender, recipient,
                String.format(findMessageByKey(NotificationMessageEnum.INVITE_GROUP),
                        sender.getName(), groupName), NotificationTypeEnum.INVITE_GROUP);
        notification.setExtraValue(groupName);
        return notification;
    }


    public Notification createInviteGroupAccepted(User sender, User recipient, String groupName) {
        Notification notification = createNotification(sender, recipient,
                String.format(findMessageByKey(NotificationMessageEnum.ACCEPT_INVITE_GROUP),
                        sender.getName(), groupName), NotificationTypeEnum.ACCEPT_INVITE_GROUP);
        notification.setExtraValue(groupName);
        return notification;
    }


    public Notification createInviteGroupDeclined(User sender, User recipient, String groupName) {
        Notification notification = createNotification(sender, recipient,
                String.format(findMessageByKey(NotificationMessageEnum.DECLINE_INVITE_GROUP),
                        sender.getName(), groupName), NotificationTypeEnum.DECLINE_INVITE_GROUP);
        notification.setExtraValue(groupName);
        return notification;
    }
}
