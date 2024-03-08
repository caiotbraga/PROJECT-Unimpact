package unicap.br.unimpact.service.services.project.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.api.dtos.request.ProjectRequest;
import unicap.br.unimpact.domain.entities.MetadataProject;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.repository.MetadataProjectRepository;
import unicap.br.unimpact.repository.StatusProjectRepository;
import unicap.br.unimpact.service.auxiliary.ModelMap;

import java.util.Date;
import java.util.Map;

@Component
public class MetadataProjectRegister {

    @Autowired
    MetadataProjectRepository repository;


    public MetadataProject register(ProjectRequest projectRequest) {
        MetadataProject metadataProject = new MetadataProject();
        ModelMap.map(projectRequest, metadataProject);
        metadataProject.setId(null);
        return repository.save(metadataProject);
    }

    public MetadataProject register(Project project, Map<String, Object> projectRequest) {
        MetadataProject metadataProject = new MetadataProject();
        ModelMap.map(project.getMetadataProject(), metadataProject);
        ModelMap.map(projectRequest, metadataProject);
        metadataProject.setId(null);
        return repository.save(metadataProject);
    }

}
