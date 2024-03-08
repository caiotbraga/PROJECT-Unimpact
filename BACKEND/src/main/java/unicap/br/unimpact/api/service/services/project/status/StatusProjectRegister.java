package unicap.br.unimpact.service.services.project.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.domain.entities.StateProject;
import unicap.br.unimpact.domain.entities.StatusProject;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.domain.enums.StateEnum;
import unicap.br.unimpact.domain.enums.StatusEnum;
import unicap.br.unimpact.repository.ProjectRepository;
import unicap.br.unimpact.repository.StateProjectRepository;
import unicap.br.unimpact.repository.StatusProjectRepository;
import unicap.br.unimpact.service.auxiliary.ProjectFlowControl;
import unicap.br.unimpact.service.exceptions.business.InvalidRequestException;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.exceptions.business.UnauthorizedException;
import unicap.br.unimpact.service.services.others.AuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class StatusProjectRegister {

    @Autowired
    StatusProjectRepository repository;

    @Autowired
    StateProjectRepository stateProjectRepository;

    @Autowired
    ProjectRepository projectRepository;

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

    public StateProject initFlow() {
        ProjectFlowControl projectFlow = new ProjectFlowControl();

        StateProject stateProject = new StateProject();
        stateProject.setState(projectFlow.getActualState());

        StatusProject statusProject = createStatusProject(projectFlow.getActualStatus());
        stateProject.setStatus(new ArrayList<>(List.of(statusProject)));
        stateProject.setActualStatus(statusProject);
        stateProjectRepository.save(stateProject);

        statusProject.setState(stateProject);
        repository.save(statusProject);

        return stateProject;
    }


    public StatusProject nextStatus(Long stateId, StatusEnum nextStatus) {
        StateProject actualState = stateProjectRepository.getById(stateId);
        ProjectFlowControl projectFlow = new ProjectFlowControl(actualState.getState(),
                searcher.getLastStatus(actualState).getStatus());

        this.securityVerify(projectFlow);
        this.securityVerify(actualState.getProject());

        if (projectFlow.getNextStatus().contains(nextStatus)) {

            StatusProject statusProject;
            if (actualState.getState().equals(projectFlow.getNextState())) {
                statusProject = this.createStatusProject(actualState, nextStatus);
            } else {
                statusProject = this.createStateAndStatusProject(actualState.getProject(),
                        projectFlow.getNextState(), nextStatus);
            }

            return statusProject;
        } else {
            throw new NotFoundException(StatusEnum.class);
        }
    }


    private StatusProject createStatusProject(StatusEnum status) {
        StatusProject statusProject = new StatusProject();

        statusProject.setStatus(status);

        return repository.save(statusProject);
    }


    private StatusProject createStatusProject(Project project, StatusEnum status) {
        StatusProject statusProject = new StatusProject();
        StatusProject lastStatus = searcher.getLastStatus(project);

        statusProject.setStatus(status);
        if (!Objects.isNull(lastStatus.getMiddleEmployee()))
            statusProject.setMiddleEmployee(lastStatus.getMiddleEmployee());
        if (!Objects.isNull(lastStatus.getDepartments()) && !lastStatus.getDepartments().isEmpty()) {
            statusProject.setDepartments(new ArrayList<>());
            statusProject.getDepartments().addAll(lastStatus.getDepartments());
        }

        return repository.save(statusProject);
    }


    private StatusProject createStateAndStatusProject(Project project, StateEnum state, StatusEnum status) {

        // Create State
        StateProject stateProject = new StateProject();
        stateProject.setState(state);

        // Set Status and Project
        StatusProject statusProject = createStatusProject(project, status);
        stateProject.setStatus(new ArrayList<>(List.of(statusProject)));
        stateProject.setActualStatus(statusProject);
        stateProject.setProject(project);
        stateProjectRepository.save(stateProject);

        statusProject.setState(stateProject);
        repository.save(statusProject);

        // Update Project
        project.setActualState(stateProject);
        projectRepository.save(project);

        return statusProject;
    }


    private StatusProject createStatusProject(StateProject state, StatusEnum status) {
        StatusProject statusProject = this.createStatusProject(state.getProject(), status);

        state.getStatus().add(statusProject);
        state.setActualStatus(statusProject);
        stateProjectRepository.save(state);

        statusProject.setState(state);
        repository.save(statusProject);

        if (state.getState().equals(StateEnum.PROPOSAL) &&
                (statusProject.getStatus().equals(StatusEnum.ACCEPT) ||
                        statusProject.getStatus().equals(StatusEnum.RECUSED))) {
            state.getProject().setNew(false);
            projectRepository.save(state.getProject());
        }

        return statusProject;
    }

}
