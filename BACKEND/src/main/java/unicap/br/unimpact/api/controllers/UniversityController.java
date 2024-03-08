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
import unicap.br.unimpact.api.dtos.request.UniversityRequest;
import unicap.br.unimpact.api.dtos.response.MessageResponse;
import unicap.br.unimpact.api.dtos.response.PhysicalPersonResponse;
import unicap.br.unimpact.api.dtos.response.UniversityResponse;
import unicap.br.unimpact.domain.entities.University;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.notification.NotificationService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;
import unicap.br.unimpact.service.services.person.university.UniversityService;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {

    @Autowired
    UniversityService service;

    @Autowired
    PhysicalPersonService physicalPersonService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AuthService authService;


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/")
    @Secured({"ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "edit university", response = UniversityResponse.class)
    public ResponseEntity<?> edit(@RequestBody UniversityRequest request) {

        Long currentUserId = authService.getCurrentUser().getId();
        return service.getResponseByEntity(service.edit(currentUserId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/patch", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "partial edit university", response = UniversityResponse.class)
    public ResponseEntity<?> partialEdit(@RequestBody Map<String, Object> request) {

        Long currentUserId = authService.getCurrentUser().getId();
        return service.getResponseByEntity(service.edit(currentUserId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/representatives")
    @Secured({"ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "return all representatives of juridical person", response = PhysicalPersonResponse.class,
            responseContainer = "List")
    public ResponseEntity<?> getAllRepresentatives() {

        Long userId = authService.getCurrentUser().getId();
        return physicalPersonService.getResponseByList(service.getAllRepresentatives(userId),
                MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/")
    @Secured({"ROLE_MIDDLE_MANAGER", "ROLE_MIDDLE_EMPLOYEE", "ROLE_EXECUTING_EMPLOYEE"})
    @ApiOperation(value = "return juridical person", response = UniversityResponse.class)
    public ResponseEntity<?> get() {

        User currentUser = authService.getCurrentUser();

        if (currentUser.getFunctions().contains(FunctionEnum.ROLE_MIDDLE_MANAGER.name())) {
            return service.getResponseByEntity(service.get(currentUser.getId()), MessageEnum.FOUND);
        } else if (currentUser.getFunctions().contains(FunctionEnum.ROLE_MIDDLE_EMPLOYEE.name()) ||
                currentUser.getFunctions().contains(FunctionEnum.ROLE_EXECUTING_EMPLOYEE.name())) {
            return service.getResponseByEntity(authService.getUniversityUnicap(), MessageEnum.FOUND);
        }
        throw new NotFoundException(University.class);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @DeleteMapping("/representatives")
    @Secured({"ROLE_MIDDLE_MANAGER"})
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
    @Secured({"ROLE_MIDDLE_MANAGER"})
    @ApiOperation(value = "delete university", response = UniversityResponse.class)
    public ResponseEntity<?> delete() {

        Long userId = authService.getCurrentUser().getId();
        return service.getResponseByEntity(service.delete(userId), MessageEnum.DELETED);
    }

}
