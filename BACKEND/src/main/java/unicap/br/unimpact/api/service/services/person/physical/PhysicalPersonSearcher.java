package unicap.br.unimpact.service.services.person.physical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.repository.PhysicalPersonRepository;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;

import java.util.Optional;

@Component
public class PhysicalPersonSearcher {

    @Autowired
    PhysicalPersonRepository repository;


    public PhysicalPerson getByEmail(String email) {
        Optional<PhysicalPerson> physicalPerson = repository.findByEmail(email);
        if (physicalPerson.isEmpty()) {
            throw new NotFoundException(PhysicalPerson.class);
        }
        return physicalPerson.get();
    }

}
