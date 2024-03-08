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
public class StatusProjectSearcher {

    @Autowired
    StatusProjectRepository repository;

    @Autowired
    StateProjectRepository stateProjectRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    AuthService authService;


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


    public List<StatusEnum> getNextStatus(Long stateId) {
        StateProject lastState = stateProjectRepository.getById(stateId);
        StatusProject lastStatus = getLastStatus(stateId);

        ProjectFlowControl projectFlow = new ProjectFlowControl(lastState.getState(), lastStatus.getStatus());

        try {
            this.securityVerify(projectFlow);
            this.securityVerify(lastState.getProject());
        } catch (UnauthorizedException ignored) {
            return new ArrayList<>();
        }

        return projectFlow.getNextStatus();
    }

    public StatusProject getLastStatus(Long stateId) {
        StateProject lastState = stateProjectRepository.getById(stateId);
        return lastState.getStatus().isEmpty() ? null :
                lastState.getStatus().get(lastState.getStatus().size() - 1);
    }


    public StatusProject getLastStatus(StateProject stateProject) {
        return getLastStatus(stateProject.getId());
    }


    public StatusProject getLastStatus(Project project) {
        return getLastStatus(project.getActualState().getId());
    }

    public List<StatusProject> getHistoryStatusByState(Long stateId) {
        return this.repository.getAllByState_Id(stateId);
    }
}
