package unicap.br.unimpact.api.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import unicap.br.unimpact.api.config.documentation.swagger.ApiRoleAccessNotes;
import unicap.br.unimpact.api.dtos.basic.NotificationInviteEnum;
import unicap.br.unimpact.api.dtos.basic.NotificationResponseEnum;
import unicap.br.unimpact.api.dtos.response.NotificationResponse;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.notification.NotificationService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    @Autowired
    NotificationService service;

    @Autowired
    AuthService authService;


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{notificationId}")
    @ApiOperation(value = "return notification by id", response = NotificationResponse.class)
    public ResponseEntity<?> getById(@PathVariable(value = "notificationId") Long notificationId) {

        return service.getResponseByEntity(service.get(notificationId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/received")
    @ApiOperation(value = "return all notifications receiveds", response = NotificationResponse.class, responseContainer = "List")
    public ResponseEntity<?> getAllReceived() {

        String userEmail = authService.getCurrentUser().getEmail();
        return service.getResponseByList(service.getAllByRecipient(userEmail), MessageEnum.FOUND);
    }

    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sent")
    @ApiOperation(value = "return all notifications sents", response = NotificationResponse.class, responseContainer = "List")
    public ResponseEntity<?> getAllSent() {

        String userEmail = authService.getCurrentUser().getEmail();
        return service.getResponseByList(service.getAllBySender(userEmail), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/invite")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE", "ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "sent invite", response = NotificationResponse.class)
    public ResponseEntity<?> invite(@RequestParam(value = "recipientsEmail") List<String> recipientsEmail,
                                    @RequestParam(value = "groupId", required = false) Long groupId,
                                    @RequestParam(value = "notificationType") NotificationInviteEnum notificationType) {

        String userEmail = authService.getCurrentUser().getEmail();

        return switch (notificationType) {
            case INVITE_REPRESENTATIVE -> service.getResponseByList(service.inviteRepresentatives(userEmail,
                    recipientsEmail), MessageEnum.SENT);
            case INVITE_GROUP -> service.getResponseByList(service.inviteGroupMembers(userEmail, groupId,
                    recipientsEmail), MessageEnum.SENT);
        };
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/invite/response")
    @ApiOperation(value = "response invite", response = NotificationResponse.class)
    public ResponseEntity<?> responseInvite(@RequestParam(value = "notificationId") Long notificationId,
                                            @RequestParam(value = "notificationType") NotificationResponseEnum notificationType) {

        //TODO: service.validate(notificationId, notificationType.name());
        switch (notificationType) {
            case ACCEPT_INVITE_REPRESENTATIVE -> service.acceptInviteRepresentative(notificationId);
            case ACCEPT_INVITE_GROUP -> service.acceptInviteGroup(notificationId);
            case DECLINE_INVITE_REPRESENTATIVE -> service.declineInviteRepresentative(notificationId);
            case DECLINE_INVITE_GROUP -> service.declineInviteGroup(notificationId);
        }

        return service.getResponseSimple(MessageEnum.SENT);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/")
    @ApiOperation(value = "delete notification", response = NotificationResponse.class)
    public ResponseEntity<?> delete(@RequestParam(value = "notifications") List<Long> notificationsId) {

        return service.getResponseByList(service.deleteAll(notificationsId), MessageEnum.DELETED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/received")
    @ApiOperation(value = "delete all notification received", response = NotificationResponse.class,
            responseContainer = "List")
    public ResponseEntity<?> deleteAllReceivedByEmail() {

        String userEmail = authService.getCurrentUser().getEmail();
        return service.getResponseByList(service.deleteAllReceivedByEmail(userEmail),
                MessageEnum.DELETED);
    }

    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/sent")
    @ApiOperation(value = "delete all notification sent", response = NotificationResponse.class,
            responseContainer = "List")
    public ResponseEntity<?> deleteAllSentByEmail() {

        String userEmail = authService.getCurrentUser().getEmail();
        return service.getResponseByList(service.deleteAllSentByEmail(userEmail),
                MessageEnum.DELETED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/check/viewed")
    @ApiOperation(value = "check viwed notification", response = NotificationResponse.class)
    public ResponseEntity<?> checkViwedNotification(@RequestParam(value = "notificationId") Long notificationId) {

        return service.getResponseByEntity(service.setViwed(notificationId),
                MessageEnum.EDITED);
    }

}
