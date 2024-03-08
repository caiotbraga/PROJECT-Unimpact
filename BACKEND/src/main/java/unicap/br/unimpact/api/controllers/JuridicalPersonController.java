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
import unicap.br.unimpact.api.dtos.request.JuridicalPersonRequest;
import unicap.br.unimpact.api.dtos.response.JuridicalPersonResponse;
import unicap.br.unimpact.api.dtos.response.MessageResponse;
import unicap.br.unimpact.api.dtos.response.PhysicalPersonResponse;
import unicap.br.unimpact.domain.entities.JuridicalPerson;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.person.juridical.JuridicalPersonService;
import unicap.br.unimpact.service.services.notification.NotificationService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/person/juridical")
@RequiredArgsConstructor
public class JuridicalPersonController {

    @Autowired
    JuridicalPersonService service;

    @Autowired
    PhysicalPersonService physicalPersonService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AuthService authService;


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/")
    @Secured({"ROLE_REQUESTING_MANAGER"})
    @ApiOperation(value = "edit juridical person", response = JuridicalPersonResponse.class)
    public ResponseEntity<?> edit(@RequestBody JuridicalPersonRequest request) {

        Long currentUserId = authService.getCurrentUser().getId();
        return service.getResponseByEntity(service.edit(currentUserId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/patch", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_REQUESTING_MANAGER"})
    @ApiOperation(value = "partial edit juridical person", response = JuridicalPersonResponse.class)
    public ResponseEntity<?> partialEdit(@RequestBody Map<String, Object> request) {

        Long currentUserId = authService.getCurrentUser().getId();
        return service.getResponseByEntity(service.edit(currentUserId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_EMPLOYEE"})
    @ApiOperation(value = "return juridical person", response = JuridicalPersonResponse.class)
    public ResponseEntity<?> get() {

        User currentUser = authService.getCurrentUser();

        if (currentUser.getFunctions().contains(FunctionEnum.ROLE_REQUESTING_MANAGER.name())) {
            return service.getResponseByEntity(service.get(currentUser.getId()), MessageEnum.FOUND);
        } else if (currentUser.getFunctions().contains(FunctionEnum.ROLE_REQUESTING_EMPLOYEE.name())) {
            return service.getResponseByList(service.getByRepresentative(currentUser.getEmail()), MessageEnum.FOUND);
        }

        throw new NotFoundException(JuridicalPerson.class);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/representatives")
    @Secured({"ROLE_REQUESTING_MANAGER"})
    @ApiOperation(value = "return all representatives of juridical person", response = PhysicalPersonResponse.class,
            responseContainer = "List")
    public ResponseEntity<?> getAllRepresentatives() {

        Long userId = authService.getCurrentUser().getId();
        return physicalPersonService.getResponseByList(service.getAllRepresentatives(userId),
                MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @DeleteMapping("/representatives")
    @Secured({"ROLE_REQUESTING_MANAGER"})
    @ApiOperation(value = "remove representative")
    public ResponseEntity<?> removeRepresentative(@RequestParam(value = "representativeEmail") String representativeEmail) {

        User user = authService.getCurrentUser();
        service.removeRepresentative(user.getId(), representativeEmail);
        notificationService.removalNotice(user.getEmail(), representativeEmail);

        return ResponseEntity.ok(new MessageResponse(MessageEnum.DELETED, "representative"));
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/")
    @Secured({"ROLE_REQUESTING_MANAGER"})
    @ApiOperation(value = "delete juridical person", response = JuridicalPersonResponse.class)
    public ResponseEntity<?> delete() {

        Long userId = authService.getCurrentUser().getId();
        return service.getResponseByEntity(service.delete(userId), MessageEnum.DELETED);
    }

}
