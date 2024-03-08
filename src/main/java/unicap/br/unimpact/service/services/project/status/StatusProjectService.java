package unicap.br.unimpact.service.services.project.status;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.response.StatusProjectResponse;
import unicap.br.unimpact.domain.entities.MetadataProject;
import unicap.br.unimpact.domain.entities.StateProject;
import unicap.br.unimpact.domain.entities.StatusProject;
import unicap.br.unimpact.domain.enums.StatusEnum;
import unicap.br.unimpact.repository.StatusProjectRepository;
import unicap.br.unimpact.service.interfaces.GenericCRUD;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class StatusProjectService extends GenericCRUD<StatusProject, StatusProjectResponse> {

    @Autowired
    StatusProjectRepository repository;

    @Autowired
    StatusProjectRegister register;

    @Autowired
    StatusProjectSearcher searcher;

    @Autowired
    StatusProjectEditor editor;


    @PostConstruct
    public void init() {
        this.init(StatusProject.class, StatusProjectResponse.class,
                this.repository, null);
    }

    public StateProject initFlow() {
        return register.initFlow();
    }

    public StatusProject nextStatus(Long stateId, StatusEnum nextStatus) {
        return register.nextStatus(stateId, nextStatus);
    }

    public List<StatusEnum> getNextStatus(Long stateId) {
        return searcher.getNextStatus(stateId);
    }

    public StatusProject getLastStatus(Long stateId) {
        return searcher.getLastStatus(stateId);
    }

    public StatusProject getLastStatus(StateProject stateProject) {
        return searcher.getLastStatus(stateProject);
    }

    public List<StatusProject> getHistoryStatusByState(Long stateId) {
        return searcher.getHistoryStatusByState(stateId);
    }

    public void addMetadata(Long projectId, MetadataProject metadata) {
        editor.addMetadata(projectId, metadata);
    }

    public void assignToMe(Long projectId) {
        editor.assignToMe(projectId);
    }

    public void assignToDepartment(Long projectId, Long groupId) {
        editor.assignToDepartment(projectId, groupId);
    }

}
