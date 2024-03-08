package unicap.br.unimpact.service.services.project.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.domain.enums.StateEnum;
import unicap.br.unimpact.domain.enums.StatusEnum;
import unicap.br.unimpact.repository.ProjectRepository;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.exceptions.business.UnauthorizedException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.group.GroupService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ProjectSearcher {

    @Autowired
    ProjectRepository repository;

    @Autowired
    GroupService groupService;

    @Autowired
    AuthService authService;


    public void securityVerify(Project project) {
        User user = authService.getCurrentUser();
        if (!project.getOwnerEmail().equals(user.getEmail()) &&
                (!Objects.isNull(project.getResponsible().getPhysicalPersonResponsible()) && !project.getResponsible()
                        .getPhysicalPersonResponsible().getEmail().equals(user.getEmail())) &&
                !groupService.memberInGroupList(project.getResponsible().getGroupsResponsibles(), user.getEmail())) {

            throw new UnauthorizedException(Project.class);
        }
    }

    @Transactional
    public Project get(Long id) {
        Optional<Project> entity = this.repository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new NotFoundException(Project.class);
        }
    }


    public List<Project> getAllProjectsByOwner(String ownerEmail) {
        return this.repository.findAllByOwnerEmail(ownerEmail);
    }


    public List<Project> getAllProjectsByGroup(Long groupId) {
        return this.repository.findAllByGroupId(groupId);
    }


    public List<Project> getAllProjectsByDepartment(Long groupId) {
        return this.repository.findAllByDepartmentId(groupId);
    }


    public List<Project> getAllProjectsByResponsible(String responsibleEmail) {
        List<Project> projects = this.repository.findAllByRepresentativeEmail(responsibleEmail);
        projects.addAll(this.repository.findAllByRepresentativeEmailInGroup(responsibleEmail));
        return projects;
    }


    public List<Project> getAllProjectsByResponsible(List<Project> projects) {
        List<Project> projectResponse = new ArrayList<>();

        projects.forEach(p -> {
            try {
                this.securityVerify(p);
                projectResponse.add(p);
            } catch (UnauthorizedException ignored) {
            }
        });

        return projectResponse;
    }

    public List<Project> getAllProjectsByResponsibleMiddleEmployee(User user) {
        return this.repository.findAllByMiddleEmployee(user.getEmail());
    }


    public List<Project> getAllProjectsByResponsibleDepartment(User user) {
        return this.repository.findAllByDepartmentParticipate(user.getEmail());
    }

    public List<Project> getAllProjectsByResponsibleDepartmentAndInDevelopment() {
        User user = authService.getCurrentUser();
        return this.repository.findAllByDepartmentParticipateInDevelopment(user.getEmail());
    }


    public List<Project> getAllProjectsByResponsibleUniversity(StatusEnum status) {
        List<Project> projects = this.getAllProjectsByResponsibleUniversity();
        List<Project> projectResponse = new ArrayList<>();

        for (Project project : projects) {
            if (project.getActualState().getActualStatus().getStatus().equals(status)) {
                projectResponse.add(project);
            }
        }
        return projectResponse;
    }


    public List<Project> getAllProjectsByResponsibleUniversity(StateEnum state) {
        List<Project> projects = this.getAllProjectsByResponsibleUniversity();
        List<Project> projectResponse = new ArrayList<>();

        for (Project project : projects) {
            if (project.getActualState().getState().equals(state)) {
                projectResponse.add(project);
            }
        }
        return projectResponse;
    }


    public List<Project> getAllProjectsByResponsibleUniversity(StateEnum state, StatusEnum status) {
        List<Project> projects = this.getAllProjectsByResponsibleUniversity();
        List<Project> projectResponse = new ArrayList<>();

        for (Project project : projects) {
            if (project.getActualState().getState().equals(state) &&
                    project.getActualState().getActualStatus().getStatus().equals(status)) {
                projectResponse.add(project);
            }
        }
        return projectResponse;
    }


    public List<Project> getAllProjectsByResponsibleUniversity() {
        User user = authService.getCurrentUser();
        List<Project> projects;

        if (user.getFunctions().contains(FunctionEnum.ROLE_MIDDLE_EMPLOYEE.name())) {
            projects = this.getAllProjectsByResponsibleMiddleEmployee(user);
        } else {
            projects = this.getAllProjectsByResponsibleDepartment(user);
        }

        return projects;
    }


    public List<Project> getAllProjectsByActualStatus(StatusEnum status) {
        return this.repository.findAllByActualState_ActualStatus_Status(status);
    }


    public List<Project> getAllProjectsByActualState(StateEnum state) {
        return this.repository.findAllByActualState_State(state);
    }


    public List<Project> getAllProjectsByActualStateAndStatus(StateEnum state, StatusEnum status) {
        return this.repository.findAllByActualState_StateAndActualState_ActualStatus_Status(state, status);
    }


    public List<Project> getAllProjects() {
        return this.repository.findAllProjects();
    }

}
