package unicap.br.unimpact.service.services.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Group;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.repository.GroupRepository;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

@Component
public class GroupRemover {

    @Autowired
    GroupRepository repository;

    @Autowired
    PhysicalPersonService physicalPersonService;

    @Autowired
    AuthService authService;


    public void removeMember(Long groupId, String recipient) {
        Group group = this.repository.getById(groupId);
        PhysicalPerson physicalPerson = this.physicalPersonService.getByEmail(recipient);

        if (!group.getMembers().contains(physicalPerson)) {
            throw new NotFoundException(PhysicalPerson.class);
        }

        group.getMembers().remove(physicalPerson);
        // TODO: caso a pessoa fisica faça parte de outra empresa não remover sua função
        this.physicalPersonService.removeFunction(physicalPerson, FunctionEnum.ROLE_REQUESTING_EMPLOYEE);

        this.repository.save(group);
    }

}
