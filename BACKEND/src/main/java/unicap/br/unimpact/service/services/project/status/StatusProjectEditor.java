package unicap.br.unimpact.service.services.project.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.*;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.domain.enums.StateEnum;
import unicap.br.unimpact.domain.enums.StatusEnum;
import unicap.br.unimpact.repository.*;
import unicap.br.unimpact.service.auxiliary.ProjectFlowControl;
import unicap.br.unimpact.service.exceptions.business.InvalidRequestException;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.exceptions.business.UnauthorizedException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.project.state.StateProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class StatusProjectEditor {

    @Autowired
    StatusProjectRepository repository;

    @Autowired
    StateProjectRepository stateProjectRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    StateProjectService stateProjectService;

    @Autowired
    PhysicalPersonRepository physicalPersonRepository;


    @Autowired
    AuthService authService;

    @Autowired
    StatusProjectSearcher searcher;


    public void securityVerify(ProjectFlowControl projectFlow) {
        User user = authService.getCurrentUser();
        boolean functionAuthorized = false;

        for (String function : user.getFunctions()) {
            if (projectFlow.getAuthorizedUsers().contains(FunctionEnum.valueOf(function))) {
                functionAuthorized = true;
            }
        }
        if (!functionAuthorized)
            throw new UnauthorizedException(Project.class);
    }


    public void securityVerify(Project project) {
        User user = authService.getCurrentUser();

        if (user.getFunctions().contains(FunctionEnum.ROLE_MIDDLE_EMPLOYEE.name())) {
            if (Objects.isNull(project.getActualState().getActualStatus().getMiddleEmployee())) {
                throw new InvalidRequestException("To perform this action it is necessary to assign the project to your " +
                        "responsibility first!");
            } else if (!Objects.equals(project.getActualState().getActualStatus().getMiddleEmployee().getEmail(), user.getEmail())) {
                throw new InvalidRequestException("This project is under the supervision of another employee!");
            }
        }
    }

    public void addMetadata(Long projectId, MetadataProject metadata) {
        Project project = projectRepository.getById(projectId);
        StatusProject status = searcher.getLastStatus(project.getActualState());

        if (Objects.isNull(status.getHistoryMetadataProject()) ||
                status.getHistoryMetadataProject().isEmpty()) {
            status.setHistoryMetadataProject(new ArrayList<>());
        }

        status.getHistoryMetadataProject().add(metadata);
        this.repository.save(status);
    }


    public void assignToMe(Long projectId) {
        Project project = projectRepository.getById(projectId);
        User user = authService.getCurrentUser();
        if (project.getActualState().getState().equals(StateEnum.DRAFT) &&
                project.getActualState().getActualStatus().getStatus().equals(StatusEnum.IN_REVIEW)) {
            StatusProject status = searcher.getLastStatus(project.getActualState());

            status.setMiddleEmployee(physicalPersonRepository.getById(user.getId()));
            this.repository.save(status);
        } else {
            throw new InvalidRequestException("You can only assign projects to yourself with status = DRAFT and status = IN_REVIEW");
        }
    }


    public void assignToDepartment(Long projectId, Long groupId) {
        Project project = projectRepository.getById(projectId);
        if (project.getActualState().getState().equals(StateEnum.DRAFT) &&
                project.getActualState().getActualStatus().getStatus().equals(StatusEnum.ACCEPT)) {
            StatusProject status = searcher.getLastStatus(project.getActualState());

            Group group = groupRepository.getById(groupId);
            status.setDepartments(new ArrayList<>(List.of(group)));
            this.repository.save(status);
        } else {
            throw new InvalidRequestException("You can only assign projects to departments with status = DRAFT and status = ACCEPT");
        }
    }

}
