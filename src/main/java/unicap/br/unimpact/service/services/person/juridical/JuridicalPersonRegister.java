package unicap.br.unimpact.service.services.person.juridical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.JuridicalPerson;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.repository.JuridicalPersonRepository;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class JuridicalPersonRegister {

    @Autowired
    JuridicalPersonRepository repository;

    @Autowired
    PhysicalPersonService physicalPersonService;


    public void registerNewRepresentative(JuridicalPerson juridicalPerson, String recipient) {
        PhysicalPerson physicalPerson = physicalPersonService.getByEmail(recipient);

        if (Objects.isNull(juridicalPerson.getRepresentatives()))
            juridicalPerson.setRepresentatives(new ArrayList<>());

        juridicalPerson.getRepresentatives().add(physicalPerson);
        physicalPersonService.addFunction(physicalPerson, FunctionEnum.ROLE_REQUESTING_EMPLOYEE);

        this.repository.save(juridicalPerson);
    }

}
