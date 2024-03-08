package unicap.br.unimpact.service.services.project.state;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.response.StateProjectResponse;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.domain.entities.StateProject;
import unicap.br.unimpact.repository.StateProjectRepository;
import unicap.br.unimpact.service.interfaces.GenericCRUD;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class StateProjectService extends GenericCRUD<StateProject, StateProjectResponse> {


    @Autowired
    StateProjectRepository repository;

    @Autowired
    StateProjectSearcher searcher;

    @Autowired
    StateProjectEditor editor;


    @PostConstruct
    public void init() {
        this.init(StateProject.class, StateProjectResponse.class,
                this.repository, null);
    }

    public StateProject getLastState(Long projectId) {
        return searcher.getLastState(projectId);
    }

    public List<StateProject> getHistoryStates(Long projectId) {
        return searcher.getHistoryStates(projectId);
    }

    public void setProject(StateProject state, Project project) {
        editor.setProject(state, project);
    }

}
