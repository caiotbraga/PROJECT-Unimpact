package unicap.br.unimpact.service.services.project.project;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.request.ProjectRequest;
import unicap.br.unimpact.api.dtos.request.params.project.RegisterProjectGroupParam;
import unicap.br.unimpact.api.dtos.request.params.project.RegisterProjectParam;
import unicap.br.unimpact.api.dtos.response.ProjectResponse;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.domain.entities.Responsible;
import unicap.br.unimpact.domain.enums.StateEnum;
import unicap.br.unimpact.domain.enums.StatusEnum;
import unicap.br.unimpact.repository.ProjectRepository;
import unicap.br.unimpact.service.interfaces.GenericCRUD;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@AllArgsConstructor
public class ProjectService extends GenericCRUD<Project, ProjectResponse> {

    @Autowired
    ProjectRepository repository;

    @Autowired
    ProjectPreCondition preCondition;

    @Autowired
    ProjectRegister register;

    @Autowired
    ProjectEditor editor;

    @Autowired
    ProjectSearcher searcher;


    @PostConstruct
    public void init() {
        this.init(Project.class, ProjectResponse.class, this.repository, this.preCondition);
    }


    public Project register(RegisterProjectParam params) {
        return register.register(params);
    }

    public Project register(RegisterProjectGroupParam params) {
        return register.register(params);
    }

    public Project register(RegisterProjectParam params, Responsible responsible) {
        return register.register(params, responsible);
    }

    public Project editMetadata(Long projectId, ProjectRequest projectRequest) {
        return editor.editMetadata(projectId, projectRequest);
    }

    public Project editMetadata(Long projectId, Map<String, Object> projectRequest) {
        return editor.editMetadata(projectId, projectRequest);
    }

    public List<Project> getAllProjectsByOwner(String ownerEmail) {
        return searcher.getAllProjectsByOwner(ownerEmail);
    }

    public List<Project> getAllProjectsByGroup(Long groupId) {
        return searcher.getAllProjectsByGroup(groupId);
    }

    public List<Project> getAllProjectsByDepartment(Long groupId) {
        return searcher.getAllProjectsByDepartment(groupId);
    }

    public List<Project> getAllProjectsByResponsible(String responsibleEmail) {
        return searcher.getAllProjectsByResponsible(responsibleEmail);
    }

    public List<Project> getAllProjectsByResponsible(List<Project> projects) {
        return searcher.getAllProjectsByResponsible(projects);
    }

    public List<Project> getAllProjectsByResponsibleUniversity(StatusEnum status) {
        return searcher.getAllProjectsByResponsibleUniversity(status);
    }

    public List<Project> getAllProjectsByResponsibleUniversityInDevelopment() {
        return searcher.getAllProjectsByResponsibleDepartmentAndInDevelopment();
    }

    public List<Project> getAllProjectsByResponsibleUniversity(StateEnum state) {
        return searcher.getAllProjectsByResponsibleUniversity(state);
    }

    public List<Project> getAllProjectsByResponsibleUniversity(StateEnum state, StatusEnum status) {
        return searcher.getAllProjectsByResponsibleUniversity(state, status);
    }

    public List<Project> getAllProjectsByResponsibleUniversity() {
        return searcher.getAllProjectsByResponsibleUniversity();
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

    public Project cancel(Long projectId) {
        return editor.cancel(projectId);
    }
}
