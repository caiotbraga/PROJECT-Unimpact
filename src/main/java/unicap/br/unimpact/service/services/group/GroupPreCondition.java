package unicap.br.unimpact.service.services.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Group;
import unicap.br.unimpact.repository.GroupRepository;
import unicap.br.unimpact.repository.JuridicalPersonRepository;
import unicap.br.unimpact.service.interfaces.PreCondition;
import unicap.br.unimpact.service.exceptions.business.AlreadyExistsException;

import java.util.ArrayList;


@Component
public class GroupPreCondition implements PreCondition<Group> {

    @Autowired
    GroupRepository repository;

    @Autowired
    JuridicalPersonRepository juridicalPersonRepository;


    @Override
    public void preRegister(Group entity) {

        if (repository.existsByOwnerUserEmailAndName(entity.getOwnerUserEmail(), entity.getName())) {
            throw new AlreadyExistsException(Group.class);
        }
    }

    @Override
    public void preEdit(Group entity) {
        // pass
    }

    @Override
    public void preDelete(Group entity) {
        entity.setMembers(new ArrayList<>());
        repository.save(entity);
    }
}
