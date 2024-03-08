package unicap.br.unimpact.service.services.project.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.MetadataProject;
import unicap.br.unimpact.domain.entities.StatusProject;
import unicap.br.unimpact.repository.MetadataProjectRepository;
import unicap.br.unimpact.repository.StatusProjectRepository;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class MetadataProjectSearcher {

    @Autowired
    MetadataProjectRepository repository;

    @Autowired
    StatusProjectRepository statusProjectRepository;


    public List<MetadataProject> getHistoryByStatusId(Long statusId) {
        Optional<StatusProject> statusProject = statusProjectRepository.findById(statusId);

        if (statusProject.isEmpty()) {
            throw new NotFoundException(StatusProject.class);
        }

        return statusProject.get().getHistoryMetadataProject();
    }

}
