package unicap.br.unimpact.api.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicap.br.unimpact.api.config.documentation.swagger.ApiRoleAccessNotes;
import unicap.br.unimpact.api.dtos.response.StateProjectResponse;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.service.services.project.state.StateProjectService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/state")
@RequiredArgsConstructor
public class StateProjectController {

    @Autowired
    StateProjectService service;


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{stateId}")
    @ApiOperation(value = "get state by id", response = StateProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getState(@PathVariable("stateId") Long stateId) {

        return service.getResponseByEntity(service.get(stateId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/last/{projectId}")
    @ApiOperation(value = "get last state", response = StateProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getLastState(@PathVariable("projectId") Long projectId) {

        return service.getResponseByEntity(service.getLastState(projectId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/history/{projectId}")
    @ApiOperation(value = "get history states", response = StateProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getHistory(@PathVariable("projectId") Long projectId) {

        return service.getResponseByList(service.getHistoryStates(projectId), MessageEnum.FOUND);
    }

}
