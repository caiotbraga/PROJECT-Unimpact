package unicap.br.unimpact.service.services.project.metadata;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.request.ProjectRequest;
import unicap.br.unimpact.api.dtos.response.MetadataProjectResponse;
import unicap.br.unimpact.domain.entities.MetadataProject;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.repository.MetadataProjectRepository;
import unicap.br.unimpact.service.interfaces.GenericCRUD;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@AllArgsConstructor
public class MetadataProjectService extends GenericCRUD<MetadataProject, MetadataProjectResponse> {

    @Autowired
    MetadataProjectRepository repository;

    @Autowired
    MetadataProjectRegister register;

    @Autowired
    MetadataProjectSearcher searcher;


    @PostConstruct
    public void init() {
        this.init(MetadataProject.class, MetadataProjectResponse.class,
                this.repository, null);
    }

    public MetadataProject register(ProjectRequest projectRequest) {
        return register.register(projectRequest);
    }

    public MetadataProject register(Project project, Map<String, Object> projectRequest) {
        return register.register(project, projectRequest);
    }

    public List<MetadataProject> getHistoryByStatusId(Long statusId) {
        return searcher.getHistoryByStatusId(statusId);
    }

}
