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
import unicap.br.unimpact.api.dtos.request.ProjectRequest;
import unicap.br.unimpact.api.dtos.request.params.project.AssignToDepartmentParam;
import unicap.br.unimpact.api.dtos.request.params.project.RegisterProjectGroupParam;
import unicap.br.unimpact.api.dtos.request.params.project.RegisterProjectParam;
import unicap.br.unimpact.api.dtos.response.MessageResponse;
import unicap.br.unimpact.api.dtos.response.ProjectResponse;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.MessageEnum;
import unicap.br.unimpact.domain.enums.StateEnum;
import unicap.br.unimpact.domain.enums.StatusEnum;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.project.project.ProjectService;
import unicap.br.unimpact.service.services.project.status.StatusProjectService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    @Autowired
    ProjectService service;

    @Autowired
    StatusProjectService statusProjectService;

    @Autowired
    AuthService authService;


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE"})
    @ApiOperation(value = "register project for unique responsible person", response = ProjectResponse.class)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterProjectParam RegisterProjectParams) {

        return service.getResponseByEntity(service.register(RegisterProjectParams), MessageEnum.REGISTERED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register/group")
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE"})
    @ApiOperation(value = "register project for responsible group", response = ProjectResponse.class)
    public ResponseEntity<?> registerForGroup(@Valid @RequestBody RegisterProjectGroupParam RegisterProjectParams) {

        return service.getResponseByEntity(service.register(RegisterProjectParams), MessageEnum.REGISTERED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{projectId}")
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE"})
    @ApiOperation(value = "edit project", response = ProjectResponse.class)
    public ResponseEntity<?> edit(@PathVariable("projectId") Long projectId,
                                  @RequestBody ProjectRequest request) {

        return service.getResponseByEntity(service.editMetadata(projectId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{projectId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE"})
    @ApiOperation(value = "partial edit project", response = ProjectResponse.class)
    public ResponseEntity<?> partialEdit(@PathVariable("projectId") Long projectId,
                                         @RequestBody Map<String, Object> request) {

        return service.getResponseByEntity(service.editMetadata(projectId, request), MessageEnum.EDITED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{projectId}")
    @ApiOperation(value = "return project", response = ProjectResponse.class)
    public ResponseEntity<?> get(@PathVariable("projectId") Long projectId) {

        return service.getResponseByEntity(service.get(projectId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/by/owner")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE"})
    @ApiOperation(value = "return all projects by owner", response = ProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getByOwner() {

        User user = authService.getCurrentUser();
        return service.getResponseByList(service.getAllProjectsByOwner(user.getEmail()), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/by/responsible")
    @Secured({"ROLE_REQUESTING_SIMPLE", "ROLE_REQUESTING_EMPLOYEE"})
    @ApiOperation(value = "return all projects by responsibles", response = ProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getByResponsible(@RequestParam(value = "state", required = false) StateEnum state,
                                              @RequestParam(value = "status", required = false) StatusEnum status) {
        List<Project> projects;
        if (!Objects.isNull(state)) {
            projects = service.getAllProjectsByActualState(state);
            projects = service.getAllProjectsByResponsible(projects);
        } else if (!Objects.isNull(status)) {
            projects = service.getAllProjectsByActualStatus(status);
            projects = service.getAllProjectsByResponsible(projects);
        } else {
            String userParticipateEmail = authService.getCurrentUser().getEmail();
            projects = service.getAllProjectsByResponsible(userParticipateEmail);
        }

        return service.getResponseByList(projects, MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/by/group")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE"})
    @ApiOperation(value = "return all projects by group", response = ProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getByGroup(@RequestParam(value = "groupId") Long groupId) {

        return service.getResponseByList(service.getAllProjectsByGroup(groupId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/by/department")
    @Secured({"ROLE_MIDDLE_MANAGER", "ROLE_MIDDLE_EMPLOYEE"})
    @ApiOperation(value = "return all projects by department", response = ProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getByDepartment(@RequestParam(value = "groupId") Long groupId) {

        return service.getResponseByList(service.getAllProjectsByDepartment(groupId), MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{projectId}")
    @Secured({"ROLE_REQUESTING_MANAGER", "ROLE_REQUESTING_SIMPLE"})
    @ApiOperation(value = "cancel project", response = ProjectResponse.class)
    public ResponseEntity<?> cancel(@PathVariable("projectId") Long projectId) {

        return service.getResponseByEntity(service.cancel(projectId), MessageEnum.DELETED);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/all")
    @Secured({"ROLE_MIDDLE_EMPLOYEE", "ROLE_EXECUTING_EMPLOYEE"})
    @ApiOperation(value = "return all projects by state or status", response = ProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getAll(@RequestParam(value = "state", required = false) StateEnum state,
                                    @RequestParam(value = "status", required = false) StatusEnum status) {

        List<Project> projects;
        if (!Objects.isNull(state) && !Objects.isNull(status)) {
            projects = service.getAllProjectsByActualStateAndStatus(state, status);
        } else if (!Objects.isNull(state)) {
            projects = service.getAllProjectsByActualState(state);
        } else if (!Objects.isNull(status)) {
            projects = service.getAllProjectsByActualStatus(status);
        } else {
            projects = service.getAllProjects();
        }

        return service.getResponseByList(projects, MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/by/responsible/university")
    @Secured({"ROLE_MIDDLE_EMPLOYEE", "ROLE_EXECUTING_EMPLOYEE"})
    @ApiOperation(value = "return all projects by state or status", response = ProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getAllByResponsibleUniversity(@RequestParam(value = "state", required = false) StateEnum state,
                                                           @RequestParam(value = "status", required = false) StatusEnum status) {

        List<Project> projects;
        if (!Objects.isNull(state) && !Objects.isNull(status)) {
            projects = service.getAllProjectsByResponsibleUniversity(state, status);
        } else if (!Objects.isNull(state)) {
            projects = service.getAllProjectsByResponsibleUniversity(state);
        } else if (!Objects.isNull(status)) {
            projects = service.getAllProjectsByResponsibleUniversity(status);
        } else {
            projects = service.getAllProjectsByResponsibleUniversity();
        }

        return service.getResponseByList(projects, MessageEnum.FOUND);
    }

    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/by/responsible/university/developing")
    @Secured({"ROLE_MIDDLE_EMPLOYEE", "ROLE_EXECUTING_EMPLOYEE"})
    @ApiOperation(value = "return all projects by state or status", response = ProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> getAllByResponsibleUniversityDeveloping() {

        List<Project> projects = service.getAllProjectsByResponsibleUniversityInDevelopment();
        return service.getResponseByList(projects, MessageEnum.FOUND);
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/assign/to/me/{projectId}")
    @Secured({"ROLE_MIDDLE_EMPLOYEE"})
    @ApiOperation(value = "assign project to actual user", response = ProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> assignToMe(@PathVariable(value = "projectId") Long projectId) {

        statusProjectService.assignToMe(projectId);
        return ResponseEntity.ok(new MessageResponse(MessageEnum.ACTION_TAKEN));
    }


    @ApiRoleAccessNotes
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/assign/to/department/")
    @Secured({"ROLE_MIDDLE_EMPLOYEE"})
    @ApiOperation(value = "assign project to actual user", response = ProjectResponse.class, responseContainer = "List")
    public ResponseEntity<?> assignToDepartment(@RequestBody AssignToDepartmentParam param) {

        statusProjectService.assignToDepartment(param.getProjectId(), param.getGroupId());
        return ResponseEntity.ok(new MessageResponse(MessageEnum.ACTION_TAKEN));
    }
}
