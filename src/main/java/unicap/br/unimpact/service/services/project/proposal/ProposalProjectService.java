package unicap.br.unimpact.service.services.project.proposal;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.request.ProposalProjectRequest;
import unicap.br.unimpact.api.dtos.response.ProposalProjectResponse;
import unicap.br.unimpact.domain.entities.ProposalProject;
import unicap.br.unimpact.repository.ProposalProjectRepository;
import unicap.br.unimpact.service.interfaces.GenericCRUD;

import javax.annotation.PostConstruct;


@Service
@Slf4j
@AllArgsConstructor
public class ProposalProjectService extends GenericCRUD<ProposalProject, ProposalProjectResponse> {

    @Autowired
    ProposalProjectRepository repository;

    @Autowired
    ProposalProjectRegister register;

    @Autowired
    ProposalProjectEditor editor;

    @Autowired
    ProposalProjectSearcher searcher;

    @PostConstruct
    public void init() {
        this.init(ProposalProject.class, ProposalProjectResponse.class,
                this.repository, null);
    }

    public ProposalProject register(ProposalProjectRequest request) {
        return register.register(request);
    }

    public ProposalProject edit(Long id, ProposalProjectRequest request) {
        return editor.edit(id, request);
    }

    public ProposalProject getByProject(Long id){
        return searcher.getByProject(id);
    }
}
