package unicap.br.unimpact.service.services.project.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.StateProject;
import unicap.br.unimpact.repository.StateProjectRepository;

import java.util.List;

@Component
public class StateProjectSearcher {

    @Autowired
    StateProjectRepository repository;


    public StateProject getLastState(Long projectId) {
        List<StateProject> states = this.repository.getAllByProject_Id(projectId);
        return states.isEmpty() ? null : states.get(states.size() - 1);
    }


    public List<StateProject> getHistoryStates(Long projectId) {
        return this.repository.getAllByProject_Id(projectId);
    }

}
