package unicap.br.unimpact.api.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import unicap.br.unimpact.api.config.documentation.swagger.ApiRoleAccessNotes;
import unicap.br.unimpact.api.dtos.request.params.state.PassNextStatusParam;
import unicap.br.unimpact.api.dtos.response.MessageResponse;
import unicap.br.unimpact.api.dtos.response.MetadataProjectResponse;
import unicap.br.unimpact.api.dtos.response.StatusProjectResponse;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.domain.enums.StatusEnum;
import unicap.br.unimpact.service.services.project.metadata.MetadataProjectService;
import unicap.br.unimpact.service.services.project.status.StatusProjectService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusProjectController {

    @Autowired
    StatusProjectService service;

    @Autowired
    MetadataProjectService metadataProjectService;


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{statusId}")
    @ApiOperation(value = "get status by id", response = StatusProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getStatus(@PathVariable("statusId") Long statusId) {

        return ResponseEntity.ok(new MessageResponse(MessageEnum.FOUND,
                service.get(statusId)));
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/")
    @ApiOperation(value = "pass next status", response = StatusProjectResponse.class)
    public ResponseEntity<?> nextStatus(@RequestBody PassNextStatusParam param) {

        return service.getResponseByEntity(service.nextStatus(param.getStateId(),
                param.getNextStatus()), MessageEnum.REGISTERED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/next/{stateId}")
    @ApiOperation(value = "get next status", response = StatusEnum.class, responseContainer = "List")
    public ResponseEntity<?> getNextStatus(@PathVariable("stateId") Long stateId) {

        return ResponseEntity.ok(new MessageResponse(MessageEnum.FOUND, StatusEnum.class,
                service.getNextStatus(stateId)));
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/last/{stateId}")
    @ApiOperation(value = "get last status", response = StatusEnum.class, responseContainer = "List")
    public ResponseEntity<?> getLastStatus(@PathVariable("stateId") Long stateId) {

        return service.getResponseByEntity(service.getLastStatus(stateId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/history/{stateId}")
    @ApiOperation(value = "get history status", response = StatusProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getHistoryStatus(@PathVariable("stateId") Long stateId) {

        return service.getResponseByList(service.getHistoryStatusByState(stateId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/history/metadata/{statusId}")
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE"})
    @ApiOperation(value = "get history metadata project", response = MetadataProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getHistoryProject(@PathVariable("statusId") Long statusId) {

        return metadataProjectService.getResponseByList(metadataProjectService.getHistoryByStatusId(statusId),
                MessageEnum.FOUND);
    }

}
