package unicap.br.unimpact.service.services.project.proposal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.ProposalProject;
import unicap.br.unimpact.repository.ProjectRepository;
import unicap.br.unimpact.repository.ProposalProjectRepository;
import unicap.br.unimpact.service.services.others.AuthService;

@Component
public class ProposalProjectSearcher {

    @Autowired
    ProposalProjectRepository repository;



    public ProposalProject getByProject(Long projectId) {
        return repository.findAllByProject_Id(projectId);
    }


}
