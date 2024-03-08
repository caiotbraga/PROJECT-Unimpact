package unicap.br.unimpact.service.services.project.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.domain.entities.StateProject;
import unicap.br.unimpact.repository.StateProjectRepository;

@Component
public class StateProjectEditor {

    @Autowired
    StateProjectRepository repository;


    public void setProject(StateProject state, Project project) {
        state.setProject(project);
        repository.save(state);
    }
}
