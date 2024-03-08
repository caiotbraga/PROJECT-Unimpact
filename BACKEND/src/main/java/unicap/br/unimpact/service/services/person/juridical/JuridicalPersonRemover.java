package unicap.br.unimpact.service.services.person.juridical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.JuridicalPerson;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.repository.JuridicalPersonRepository;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

@Component
public class JuridicalPersonRemover {

    @Autowired
    JuridicalPersonRepository repository;

    @Autowired
    PhysicalPersonService physicalPersonService;


    public void removeRepresentative(Long juridicalPersonId, String recipient) {
        JuridicalPerson juridicalPerson = this.repository.getById(juridicalPersonId);
        PhysicalPerson physicalPerson = this.physicalPersonService.getByEmail(recipient);

        juridicalPerson.getRepresentatives().remove(physicalPerson);
        // TODO: caso a pessoa fisica faça parte de outra empresa não remover sua função
        this.physicalPersonService.removeFunction(physicalPerson, FunctionEnum.ROLE_REQUESTING_EMPLOYEE);

        this.repository.save(juridicalPerson);
    }

}
