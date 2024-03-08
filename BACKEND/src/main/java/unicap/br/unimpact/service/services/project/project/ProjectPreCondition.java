package unicap.br.unimpact.service.services.project.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Project;
import unicap.br.unimpact.repository.ProjectRepository;
import unicap.br.unimpact.service.interfaces.PreCondition;
import unicap.br.unimpact.service.services.others.AuthService;


@Component
public class ProjectPreCondition implements PreCondition<Project> {

    @Autowired
    ProjectRepository repository;

    @Autowired
    AuthService authService;

    @Override
    public void preRegister(Project entity) {
        // pass
    }

    @Override
    public void preEdit(Project entity) {
        // pass
    }

    @Override
    public void preDelete(Project entity) {
        // pass
    }
}
