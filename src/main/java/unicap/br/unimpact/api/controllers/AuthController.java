package unicap.br.unimpact.api.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicap.br.unimpact.api.config.documentation.swagger.ApiRoleAccessNotes;
import unicap.br.unimpact.api.dtos.request.JuridicalPersonRequest;
import unicap.br.unimpact.api.dtos.request.LoginRequest;
import unicap.br.unimpact.api.dtos.request.PhysicalPersonRequest;
import unicap.br.unimpact.api.dtos.response.JuridicalPersonResponse;
import unicap.br.unimpact.api.dtos.response.JwtResponse;
import unicap.br.unimpact.api.dtos.response.MessageResponse;
import unicap.br.unimpact.api.dtos.response.PhysicalPersonResponse;
import unicap.br.unimpact.domain.entities.JuridicalPerson;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.entities.University;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.person.juridical.JuridicalPersonService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;
import unicap.br.unimpact.service.services.person.university.UniversityService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    AuthService service;

    @Autowired
    PhysicalPersonService physicalPersonService;

    @Autowired
    JuridicalPersonService juridicalPersonService;

    @Autowired
    UniversityService universityService;

    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    @ApiOperation(value = "login", response = JwtResponse.class)
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        try {
            return service.authenticateUser(loginRequest);

        } catch (Exception e) {
            return new ResponseEntity<>("Login not done! " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registerPhysicalPerson")
    @ApiOperation(value = "register physical person", response = PhysicalPersonResponse.class,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerPhysicalPerson(@RequestBody PhysicalPersonRequest physicalPersonRequest) {

        return physicalPersonService.getResponseByEntity(physicalPersonService.register(physicalPersonRequest),
                MessageEnum.REGISTERED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registerJuridicalPerson")
    @ApiOperation(value = "register juridical person", response = JuridicalPersonResponse.class)
    public ResponseEntity<?> registerJuridicalPerson(@RequestBody JuridicalPersonRequest juridicalPersonRequest) {

        return juridicalPersonService.getResponseByEntity(juridicalPersonService.register(juridicalPersonRequest),
                MessageEnum.REGISTERED);
    }


    // TODO: permitir cadastro de outras universidades
//    @ApiRoleAccessNotes
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/registerUniversity")
//    @ApiOperation(value = "register university", response = UniversityResponse.class)
//    public ResponseEntity<?> registerUniversity(@RequestBody UniversityRequest universityRequest) {
//
//        return universityService.getResponseByEntity(universityService.register(universityRequest),
//                MessageEnum.REGISTERED);
//    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    @ApiOperation(value = "return user by email")
    public ResponseEntity<?> getUserByEmail(@RequestParam(value = "email") String email) {

        User user = this.service.getUserByEmail(email);

        if (user instanceof PhysicalPerson) {
            return physicalPersonService.getResponseByEntity((PhysicalPerson) user, MessageEnum.FOUND);
        } else if (user instanceof JuridicalPerson) {
            return juridicalPersonService.getResponseByEntity((JuridicalPerson) user, MessageEnum.FOUND);
        } else if (user instanceof University) {
            return universityService.getResponseByEntity((University) user, MessageEnum.FOUND);
        } else {
            throw new NotFoundException(User.class);
        }
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/function")
    @ApiOperation(value = "return functions of current user")
    public ResponseEntity<?> getFunctions() {

        return ResponseEntity.ok(new MessageResponse(MessageEnum.FOUND, this.service.getFunctions()));
    }

}
