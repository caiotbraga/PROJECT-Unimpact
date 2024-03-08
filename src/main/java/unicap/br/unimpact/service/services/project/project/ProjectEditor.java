package unicap.br.unimpact.service.services.project.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.api.dtos.request.ProjectRequest;
import unicap.br.unimpact.domain.entities.MetadataProject;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.StatusEnum;
import unicap.br.unimpact.repository.ProjectRepository;
import unicap.br.unimpact.repository.ResponsibleRepository;
import unicap.br.unimpact.service.exceptions.business.BlockedEditionException;
import unicap.br.unimpact.service.exceptions.business.UnauthorizedException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.project.state.StateProjectService;
import unicap.br.unimpact.service.services.project.status.StatusProjectService;
import unicap.br.unimpact.service.services.group.GroupService;
import unicap.br.unimpact.service.services.project.metadata.MetadataProjectService;

import java.util.Map;
import java.util.Objects;

@Component
public class ProjectEditor {

    @Autowired
    ProjectRepository repository;

    @Autowired
    GroupService groupService;

    @Autowired
    AuthService authService;

    @Autowired
    ResponsibleRepository responsibleRepository;

    @Autowired
    StatusProjectService statusProjectService;

    @Autowired
    StateProjectService stateProjectService;

    @Autowired
    MetadataProjectService metadataProjectService;

    @Autowired
    ProjectSearcher searcher;


    public void securityVerify(Project project) {
        User user = authService.getCurrentUser();
        if (!project.getOwnerEmail().equals(user.getEmail()) &&
                (!Objects.isNull(project.getResponsible().getPhysicalPersonResponsible()) && !project.getResponsible()
                        .getPhysicalPersonResponsible().getEmail().equals(user.getEmail())) &&
                !groupService.memberInGroupList(project.getResponsible().getGroupsResponsibles(), user.getEmail())) {

            throw new UnauthorizedException(Project.class);
        }
    }


    public void editionVerify(Project project) {
        Long lastStateId = stateProjectService.getLastState(project.getId()).getId();
        StatusEnum lastStatus = statusProjectService.getLastStatus(lastStateId).getStatus();

        if (!lastStatus.equals(StatusEnum.IN_ELABORATION)) {
            throw new BlockedEditionException(Project.class);
        }
    }

    public Project editMetadata(Long projectId, ProjectRequest projectRequest) {
        Project project = searcher.get(projectId);
        securityVerify(project);
        editionVerify(project);

        MetadataProject metadata = metadataProjectService.register(projectRequest);
        project.setMetadataProject(metadata);

        statusProjectService.addMetadata(projectId, metadata);

        return this.repository.save(project);
    }


    public Project editMetadata(Long projectId, Map<String, Object> projectRequest) {
        Project project = searcher.get(projectId);
        securityVerify(project);
        editionVerify(project);

        MetadataProject metadata = metadataProjectService.register(project, projectRequest);
        project.setMetadataProject(metadata);

        statusProjectService.addMetadata(projectId, metadata);

        return this.repository.save(project);
    }

    public Project cancel(Long projectId) {
        Project project = searcher.get(projectId);
        securityVerify(project);

        project.setActive(false);
        return project;
    }

}
