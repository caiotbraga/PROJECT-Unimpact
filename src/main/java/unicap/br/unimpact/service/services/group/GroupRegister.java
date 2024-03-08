package unicap.br.unimpact.service.services.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Group;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.repository.GroupRepository;
import unicap.br.unimpact.service.exceptions.business.AlreadyExistsException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class GroupRegister {

    @Autowired
    GroupRepository repository;

    @Autowired
    PhysicalPersonService physicalPersonService;

    @Autowired
    AuthService authService;


    public void registerNewMember(Group group, String recipient) {
        PhysicalPerson physicalPerson = this.physicalPersonService.getByEmail(recipient);

        if (Objects.isNull(group.getMembers()))
            group.setMembers(new ArrayList<>());

        if (group.getMembers().contains(physicalPerson)) {
            throw new AlreadyExistsException(PhysicalPerson.class);
        }

        group.getMembers().add(physicalPerson);

        User owner = this.authService.getUserByEmail(group.getOwnerUserEmail());
        if (owner.getFunctions().contains(FunctionEnum.ROLE_REQUESTING_MANAGER.name())) {
            this.physicalPersonService.addFunction(physicalPerson, FunctionEnum.ROLE_REQUESTING_EMPLOYEE);
        } else if (owner.getFunctions().contains(FunctionEnum.ROLE_MIDDLE_MANAGER.name())) {
            this.physicalPersonService.addFunction(physicalPerson, FunctionEnum.ROLE_EXECUTING_EMPLOYEE);
        }

        this.repository.save(group);
    }

}
