package unicap.br.unimpact.service.services.project.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.api.dtos.basic.OwnerTypeEnum;
import unicap.br.unimpact.api.dtos.request.ProjectRequest;
import unicap.br.unimpact.api.dtos.request.params.project.RegisterProjectGroupParam;
import unicap.br.unimpact.api.dtos.request.params.project.RegisterProjectParam;
import unicap.br.unimpact.domain.entities.*;
import unicap.br.unimpact.repository.ProjectRepository;
import unicap.br.unimpact.repository.ResponsibleRepository;
import unicap.br.unimpact.service.auxiliary.ModelMap;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.project.state.StateProjectService;
import unicap.br.unimpact.service.services.project.status.StatusProjectService;
import unicap.br.unimpact.service.services.group.GroupService;
import unicap.br.unimpact.service.services.person.juridical.JuridicalPersonService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;
import unicap.br.unimpact.service.services.project.metadata.MetadataProjectService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectRegister {

    @Autowired
    ProjectRepository repository;

    @Autowired
    JuridicalPersonService juridicalPersonService;

    @Autowired
    PhysicalPersonService physicalPersonService;

    @Autowired
    GroupService groupService;

    @Autowired
    AuthService authService;

    @Autowired
    ResponsibleRepository responsibleRepository;

    @Autowired
    StatusProjectService statusProjectService;

    @Autowired
    StateProjectService stateProjectService;

    @Autowired
    MetadataProjectService metadataProjectService;


    public Project register(RegisterProjectParam params) {
        Responsible responsible = new Responsible();

        User user = authService.getCurrentUser();
        PhysicalPerson physicalPerson = physicalPersonService.getByEmail(user.getEmail());
        responsible.setPhysicalPersonResponsible(physicalPerson);

        // Save Responsibles
        responsibleRepository.save(responsible);

        return this.register(params, responsible);
    }


    public Project register(RegisterProjectGroupParam params) {
        Responsible responsible = new Responsible();

        List<Group> groups = new ArrayList<>();
        for (Long groupId : params.getGroupIds()) {
            groups.add(groupService.get(groupId));
        }
        responsible.setGroupsResponsibles(groups);
        responsible.setId(null);

        // Save Responsibles
        responsibleRepository.save(responsible);
        RegisterProjectParam projectParam = new RegisterProjectParam();
        ModelMap.map(params, projectParam);

        return this.register(projectParam, responsible);
    }


    public Project register(RegisterProjectParam params, Responsible responsible) {

        Project project = new Project();

        if (params.getOwnerType().equals(OwnerTypeEnum.JURIDICAL_PERSON)) {
            JuridicalPerson juridicalPerson = juridicalPersonService.getByEmail(params.getOwnerIdentification());
            project.setOwnerEmail(juridicalPerson.getEmail());

        } else if (params.getOwnerType().equals(OwnerTypeEnum.PHYSICAL_PERSON)) {
            PhysicalPerson physicalPerson = physicalPersonService.getByEmail(params.getOwnerIdentification());
            project.setOwnerEmail(physicalPerson.getEmail());

        } else {
            throw new NotFoundException("Owner");
        }

        project.setResponsible(responsible);

        // Register metadata
        ProjectRequest projectRequest = new ProjectRequest();
        ModelMap.map(params, projectRequest);

        MetadataProject metadata = metadataProjectService.register(projectRequest);
        project.setMetadataProject(metadata);

        // Save Initial State
        StateProject initialState = statusProjectService.initFlow();
        project.setStates(new ArrayList<>(List.of(initialState)));
        project.setActualState(initialState);

        // Save Project
        repository.save(project);

        // Update State with project
        stateProjectService.setProject(initialState, project);

        // Update status metadata
        statusProjectService.addMetadata(project.getId(), metadata);

        return project;
    }

}
