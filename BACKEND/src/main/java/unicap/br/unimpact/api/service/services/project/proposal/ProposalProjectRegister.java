package unicap.br.unimpact.service.services.project.proposal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.api.dtos.request.ProposalProjectRequest;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.domain.entities.ProposalProject;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.StateEnum;
import unicap.br.unimpact.repository.ProjectRepository;
import unicap.br.unimpact.repository.ProposalProjectRepository;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.exceptions.business.UnauthorizedException;
import unicap.br.unimpact.service.services.others.AuthService;

import java.util.Optional;

@Component
public class ProposalProjectRegister {

    @Autowired
    ProposalProjectRepository repository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    AuthService authService;


    public ProposalProject register(ProposalProjectRequest request) {
        ProposalProject proposal = new ProposalProject();
        Optional<Project> project = projectRepository.findById(request.getProjectId());

        if (project.isEmpty()) {
            throw new NotFoundException(Project.class);
        }

        if (!project.get().getActualState().getState().equals(StateEnum.PROPOSAL)) {
            throw new UnauthorizedException("Action not authorized in the current state");
        }

        User user = authService.getCurrentUser();
        proposal.setDescription(request.getDescription());
        proposal.setOwnerEmail(user.getEmail());
        proposal.setProject(project.get());
        repository.save(proposal);

        return proposal;
    }


}
