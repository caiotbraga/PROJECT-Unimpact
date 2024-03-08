package unicap.br.unimpact.service.services.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.*;
import unicap.br.unimpact.domain.enums.NotificationStatusEnum;
import unicap.br.unimpact.repository.NotificationRepository;
import unicap.br.unimpact.service.auxiliary.EntityValidator;
import unicap.br.unimpact.service.auxiliary.ModelMap;
import unicap.br.unimpact.service.auxiliary.NotificationFactory;
import unicap.br.unimpact.service.exceptions.business.AlreadyExistsException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;
import unicap.br.unimpact.service.services.person.university.UniversityService;
import unicap.br.unimpact.service.services.group.GroupService;
import unicap.br.unimpact.service.services.person.juridical.JuridicalPersonService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationRegister {

    @Autowired
    NotificationRepository repository;

    @Autowired
    GroupService groupService;

    @Autowired
    JuridicalPersonService juridicalPersonService;

    @Autowired
    UniversityService universityService;

    @Autowired
    PhysicalPersonService physicalPersonService;

    @Autowired
    AuthService authService;

    @Autowired
    NotificationFactory factory;

    @Autowired
    NotificationRemover remover;


    @Transactional
    public Notification register(Object object) {
        Notification entity = new Notification();
        ModelMap.map(object, entity);
        EntityValidator.validate(entity);

        this.repository.save(entity);
        return entity;
    }


    public void removalNotice(String senderEmail, String recipientEmail) {
        PhysicalPerson representative = physicalPersonService.getByEmail(recipientEmail);
        User sender = authService.getUserByEmail(senderEmail);
        this.register(factory.createRemovalNotice(sender, representative));
    }


    public void removalNotice(String senderEmail, String recipientEmail, String groupName) {
        PhysicalPerson representative = physicalPersonService.getByEmail(recipientEmail);
        User sender = authService.getUserByEmail(senderEmail);
        this.register(factory.createRemovalNotice(sender, representative, groupName));
    }


    public void leaveNotice(String senderEmail, String recipientEmail, String groupName) {
        User ownerGroup = authService.getUserByEmail(recipientEmail);
        User sender = authService.getUserByEmail(senderEmail);
        this.register(factory.createLeaveNotice(sender, ownerGroup, groupName));
    }


    public Notification inviteRepresentative(String senderEmail, String recipientEmail) {
        PhysicalPerson representative = physicalPersonService.getByEmail(recipientEmail);
        User senderUser = authService.getUserByEmail(senderEmail);

        if ((senderUser instanceof JuridicalPerson && ((JuridicalPerson) senderUser).getRepresentatives().contains(representative)) ||
                (senderUser instanceof University && ((University) senderUser).getRepresentatives().contains(representative))) {
            throw new AlreadyExistsException(PhysicalPerson.class);
        }

        return this.register(factory.createInviteRepresentative(senderUser, representative));
    }


    public List<Notification> inviteRepresentatives(String senderEmail, List<String> recipients) {
        List<Notification> notifications = new ArrayList<>();
        for (String recipient : recipients) {
            notifications.add(this.inviteRepresentative(senderEmail, recipient));
        }

        return notifications;
    }

    public void acceptInviteRepresentative(Long notificationId) {
        Notification notification = remover.deleteNotification(notificationId, NotificationStatusEnum.ACCEPT);

        PhysicalPerson representative = physicalPersonService.getByEmail(notification.getRecipientEmail());

        User senderUser = authService.getUserByEmail(notification.getSenderEmail());

        if (senderUser instanceof JuridicalPerson && !((JuridicalPerson) senderUser).getRepresentatives().contains(representative)) {
            juridicalPersonService.addRepresentative((JuridicalPerson) senderUser, notification.getRecipientEmail());
        } else if (senderUser instanceof University && !((University) senderUser).getRepresentatives().contains(representative)) {
            universityService.addRepresentative((University) senderUser, notification.getRecipientEmail());
        }

        this.register(factory.createInviteRepresentativeAccepted(representative, senderUser));
    }


    public void declineInviteRepresentative(Long notificationId) {
        Notification notification = remover.deleteNotification(notificationId, NotificationStatusEnum.RECUSED);

        PhysicalPerson representative = physicalPersonService.getByEmail(notification.getRecipientEmail());
        User recipientUser = authService.getUserByEmail(notification.getSenderEmail());

        this.register(factory.createInviteRepresentativeDeclined(representative, recipientUser));
    }


    public Notification inviteGroupMember(String senderEmail, String recipient, Long groupId) {
        PhysicalPerson member = physicalPersonService.getByEmail(recipient);
        User senderUser = authService.getUserByEmail(senderEmail);

        Group group = groupService.get(groupId);

        if (!group.getMembers().contains(member)) {
            return this.register(factory.createInviteGroup(senderUser, member, group.getName()));
        }

        throw new AlreadyExistsException(PhysicalPerson.class);

    }


    public List<Notification> inviteGroupMembers(String senderEmail, Long groupId, List<String> recipients) {
        List<Notification> notifications = new ArrayList<>();
        for (String recipient : recipients) {
            notifications.add(this.inviteGroupMember(senderEmail, recipient, groupId));
        }

        return notifications;
    }


    public void acceptInviteGroup(Long notificationId) {
        Notification notification = remover.deleteNotification(notificationId, NotificationStatusEnum.ACCEPT);

        PhysicalPerson member = physicalPersonService.getByEmail(notification.getRecipientEmail());
        User senderUser = authService.getUserByEmail(notification.getSenderEmail());

        Group group = groupService.getGroupByUserOwnerAndName(senderUser.getEmail(),
                notification.getExtraValue());

        groupService.registerNewMember(group, notification.getRecipientEmail());

        this.register(factory.createInviteGroupAccepted(member, senderUser, group.getName()));

    }


    public void declineInviteGroup(Long notificationId) {
        Notification notification = remover.deleteNotification(notificationId, NotificationStatusEnum.RECUSED);

        PhysicalPerson member = physicalPersonService.getByEmail(notification.getRecipientEmail());
        User senderUser = authService.getUserByEmail(notification.getSenderEmail());

        this.register(factory.createInviteGroupDeclined(member, senderUser, notification.getExtraValue()));
    }

}
