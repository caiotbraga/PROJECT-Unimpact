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
import unicap.br.unimpact.api.dtos.request.PhysicalPersonRequest;
import unicap.br.unimpact.api.dtos.response.PhysicalPersonResponse;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/person/physical")
@RequiredArgsConstructor
public class PhysicalPersonController {

    @Autowired
    PhysicalPersonService service;

    @Autowired
    AuthService authService;


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/")
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE", "ROLE_EXECUTING_EMPLOYEE", "ROLE_MIDDLE_EMPLOYEE"})
    @ApiOperation(value = "edit physical person", response = PhysicalPersonResponse.class)
    public ResponseEntity<?> edit(@RequestBody PhysicalPersonRequest request) {

        Long currentUserId = authService.getCurrentUser().getId();
        return service.getResponseByEntity(service.edit(currentUserId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/patch", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE", "ROLE_EXECUTING_EMPLOYEE", "ROLE_MIDDLE_EMPLOYEE"})
    @ApiOperation(value = "partial edit physical person", response = PhysicalPersonResponse.class)
    public ResponseEntity<?> partialEdit(@RequestBody Map<String, Object> request) {

        Long currentUserId = authService.getCurrentUser().getId();
        return service.getResponseByEntity(service.edit(currentUserId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/")
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE", "ROLE_EXECUTING_EMPLOYEE", "ROLE_MIDDLE_EMPLOYEE"})
    @ApiOperation(value = "get physical person", response = PhysicalPersonResponse.class)
    public ResponseEntity<?> get() {

        Long currentUserId = authService.getCurrentUser().getId();
        return service.getResponseByEntity(service.get(currentUserId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/")
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE", "ROLE_EXECUTING_EMPLOYEE", "ROLE_MIDDLE_EMPLOYEE"})
    @ApiOperation(value = "delete physical person", response = PhysicalPersonResponse.class)
    public ResponseEntity<?> delete() {

        Long userId = authService.getCurrentUser().getId();
        return service.getResponseByEntity(service.delete(userId), MessageEnum.DELETED);
    }
}
