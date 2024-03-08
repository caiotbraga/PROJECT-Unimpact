package unicap.br.unimpact.api.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import unicap.br.unimpact.api.config.documentation.swagger.ApiRoleAccessNotes;
import unicap.br.unimpact.api.dtos.request.GroupRequest;
import unicap.br.unimpact.api.dtos.request.params.group.LeaveGroupParam;
import unicap.br.unimpact.api.dtos.request.params.group.RemoveMemberParam;
import unicap.br.unimpact.api.dtos.response.GroupResponse;
import unicap.br.unimpact.api.dtos.response.MessageResponse;
import unicap.br.unimpact.domain.entities.Group;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.notification.NotificationService;
import unicap.br.unimpact.service.services.group.GroupService;

import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    @Autowired
    GroupService service;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AuthService authService;


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE", "ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "register group", response = GroupResponse.class)
    public ResponseEntity<?> register(@RequestBody GroupRequest request) {

        return service.getResponseByEntity(service.register(request), MessageEnum.REGISTERED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{groupId}")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE", "ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "edit group", response = GroupResponse.class)
    public ResponseEntity<?> edit(@PathVariable("groupId") Long groupId,
                                  @RequestBody GroupRequest request) {

        return service.getResponseByEntity(service.edit(groupId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{groupId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE", "ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "partial edit group", response = GroupResponse.class)
    public ResponseEntity<?> partialEdit(@PathVariable("groupId") Long groupId,
                                         @RequestBody Map<String, Object> request) {

        return service.getResponseByEntity(service.edit(groupId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{groupId}")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE", "ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "return one group", response = GroupResponse.class)
    public ResponseEntity<?> get(@PathVariable("groupId") Long idGroup) {

        return service.getResponseByEntity(service.get(idGroup), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/get/by/owner")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE", "ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "return groups by owner", response = GroupResponse.class, responseContainer = "List")
    public ResponseEntity<?> getByOwner(@RequestParam(value = "groupName", required = false) String groupName) {

        String userOwnerEmail = authService.getCurrentUser().getEmail();

        if (!Objects.isNull(groupName)) {
            return service.getResponseByEntity(service.getGroupByUserOwnerAndName(userOwnerEmail, groupName), MessageEnum.FOUND);
        } else {
            return service.getResponseByList(service.getAllGroupsByUserOwner(userOwnerEmail), MessageEnum.FOUND);
        }
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/get/by/participate")
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE", "ROLE_EXECUTING_EMPLOYEE"})
    @ApiOperation(value = "return groups by participate", response = GroupResponse.class, responseContainer = "List")
    public ResponseEntity<?> getByParticipate() {

        String userParticipateEmail = authService.getCurrentUser().getEmail();
        return service.getResponseByList(service.getAllGroupsByUserParticipate(userParticipateEmail),
                MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/get/departments")
    @Secured({"ROLE_MIDDLE_EMPLOYEE", "ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "return departments of unicap", response = GroupResponse.class, responseContainer = "List")
    public ResponseEntity<?> getAllDepartments() {

        return service.getResponseByList(service.getAllDepartmentsOfUnicap(), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{groupId}")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE", "ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "delete group", response = GroupResponse.class)
    public ResponseEntity<?> delete(@PathVariable("groupId") Long groupId) {

        return service.getResponseByEntity(service.delete(groupId), MessageEnum.DELETED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/member")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE", "ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "remove member from group", response = GroupResponse.class)
    public ResponseEntity<?> removeMember(@RequestBody RemoveMemberParam removeMember) {

        Group group = service.get(removeMember.getGroupId());
        service.removeMember(group.getId(), removeMember.getMemberEmail());
        notificationService.removalNotice(group.getOwnerUserEmail(), removeMember.getMemberEmail(), group.getName());

        return ResponseEntity.ok(new MessageResponse(MessageEnum.DELETED, "member"));
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/leave")
    @Secured({"ROLE_REQUESTING_EMPLOYEE", "ROLE_EXECUTING_EMPLOYEE"})
    @ApiOperation(value = "leave the group", response = GroupResponse.class)
    public ResponseEntity<?> leaveGroup(@RequestBody LeaveGroupParam param) {

        Group group = service.get(param.getGroupId());
        User user = authService.getCurrentUser();
        service.removeMember(group.getId(), user.getEmail());
        notificationService.leaveNotice(user.getEmail(), group.getOwnerUserEmail(), group.getName());

        return ResponseEntity.ok(new MessageResponse(MessageEnum.DELETED, "member"));
    }
}