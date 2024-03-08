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
import unicap.br.unimpact.api.dtos.request.ProposalProjectRequest;
import unicap.br.unimpact.api.dtos.response.ProposalProjectResponse;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.service.services.project.proposal.ProposalProjectService;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/proposal")
@RequiredArgsConstructor
public class ProposalProjectController {

    @Autowired
    ProposalProjectService service;


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    @Secured({"ROLE_EXECUTING_EMPLOYEE"})
    @ApiOperation(value = "register proposal", response = ProposalProjectResponse.class)
    public ResponseEntity<?> register(@Valid @RequestBody ProposalProjectRequest request) {

        return service.getResponseByEntity(service.register(request), MessageEnum.REGISTERED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{proposalId}")
    @Secured({"ROLE_EXECUTING_EMPLOYEE"})
    @ApiOperation(value = "edit proposal", response = ProposalProjectResponse.class)
    public ResponseEntity<?> edit(@PathVariable("proposalId") Long proposalId,
                                  @RequestBody ProposalProjectRequest request) {

        return service.getResponseByEntity(service.edit(proposalId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{proposalId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_EXECUTING_EMPLOYEE"})
    @ApiOperation(value = "partial edit proposal", response = ProposalProjectResponse.class)
    public ResponseEntity<?> partialEdit(@PathVariable("proposalId") Long proposalId,
                                         @RequestBody Map<String, Object> request) {

        return service.getResponseByEntity(service.edit(proposalId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{proposalId}")
    @ApiOperation(value = "return proposal", response = ProposalProjectResponse.class)
    public ResponseEntity<?> get(@PathVariable("proposalId") Long proposalId) {

        return service.getResponseByEntity(service.get(proposalId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/by/project/{projectId}")
    @ApiOperation(value = "return proposal", response = ProposalProjectResponse.class)
    public ResponseEntity<?> getByProject(@PathVariable("projectId") Long projectId) {

        return service.getResponseByEntity(service.getByProject(projectId), MessageEnum.FOUND);
    }
}
