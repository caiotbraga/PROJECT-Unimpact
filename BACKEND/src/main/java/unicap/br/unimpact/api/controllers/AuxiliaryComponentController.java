package unicap.br.unimpact.api.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.domain.enums.InstitutionTypeEnum;
import unicap.br.unimpact.domain.enums.NotificationStatusEnum;
import unicap.br.unimpact.domain.enums.NotificationTypeEnum;
import unicap.br.unimpact.service.services.others.AuxiliaryComponentsService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auxiliary")
@RequiredArgsConstructor
public class AuxiliaryComponentController {

    @Autowired
    AuxiliaryComponentsService service;


    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/function/type")
    @ApiOperation(value = "return all user function", response = FunctionEnum.class)
    public ResponseEntity<?> getAllFunctionUserType() {
        return service.getAllFunctionUserType();
    }


    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/institution/type")
    @ApiOperation(value = "return all instituations types", response = InstitutionTypeEnum.class)
    public ResponseEntity<?> getAllInstitutionType() {
        return service.getAllInstitutionType();
    }


    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/notification/type")
    @ApiOperation(value = "return all notifications types", response = NotificationTypeEnum.class)
    public ResponseEntity<?> getAllNotificationType() {
        return service.getAllNotificationType();
    }


    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/notification/status")
    @ApiOperation(value = "return all notifications status", response = NotificationStatusEnum.class)
    public ResponseEntity<?> getAllNotificationStatus() {
        return service.getAllNotificationStatus();
    }
}
