package unicap.br.unimpact.service.services.person.juridical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.JuridicalPerson;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.repository.JuridicalPersonRepository;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JuridicalPersonSearcher {

    @Autowired
    JuridicalPersonRepository repository;

    @Autowired
    PhysicalPersonService physicalPersonService;

    @Autowired
    AuthService authService;


    public JuridicalPerson getByEmail(String email) {
        Optional<JuridicalPerson> juridicalPerson = repository.findByEmail(email);
        if (juridicalPerson.isEmpty()) {
            throw new NotFoundException(JuridicalPerson.class);
        }
        return juridicalPerson.get();
    }


    public List<JuridicalPerson> getByRepresentative(String email) {
        List<JuridicalPerson> juridicalPersons = repository.findAllByRepresentative(email);
        if (juridicalPersons.isEmpty()) {
            throw new NotFoundException(JuridicalPerson.class);
        }
        return juridicalPersons;
    }

    public List<PhysicalPerson> getAllRepresentatives(Long juridicalPersonId) {
        Optional<JuridicalPerson> juridicalPerson = repository.findById(juridicalPersonId);
        return juridicalPerson.isPresent() ? juridicalPerson.get().getRepresentatives() : new ArrayList<>();
    }

}
