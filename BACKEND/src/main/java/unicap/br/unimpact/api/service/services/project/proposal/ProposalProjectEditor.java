package unicap.br.unimpact.service.services.project.proposal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.api.dtos.request.ProposalProjectRequest;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.domain.entities.ProposalProject;
import unicap.br.unimpact.domain.enums.StateEnum;
import unicap.br.unimpact.domain.enums.StatusEnum;
import unicap.br.unimpact.repository.ProjectRepository;
import unicap.br.unimpact.repository.ProposalProjectRepository;
import unicap.br.unimpact.service.exceptions.business.BlockedEditionException;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.services.others.AuthService;

import java.util.Optional;

@Component
public class ProposalProjectEditor {

    @Autowired
    ProposalProjectRepository repository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    AuthService authService;


    public ProposalProject edit(Long id, ProposalProjectRequest request) {
        Optional<ProposalProject> proposal = repository.findById(id);
        Optional<Project> project = projectRepository.findById(request.getProjectId());

        if (proposal.isEmpty()) {
            throw new NotFoundException(ProposalProject.class);
        }
        if (project.isPresent() && project.get().getActualState().getState().equals(StateEnum.PROPOSAL)
                && project.get().getActualState().getActualStatus().getStatus().equals(StatusEnum.DEVELOPMENT_PLAN)) {

            proposal.get().setDescription(request.getDescription());
            repository.save(proposal.get());
        } else {
            throw new BlockedEditionException("Editing is not allowed in this status");
        }

        return proposal.get();
    }


}
