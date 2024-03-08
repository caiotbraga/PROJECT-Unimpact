package unicap.br.unimpact.service.services.person.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.JuridicalPerson;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.entities.University;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.repository.UniversityRepository;
import unicap.br.unimpact.service.exceptions.business.NotFoundException;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UniversitySearcher {

    @Autowired
    UniversityRepository repository;

    @Autowired
    PhysicalPersonService physicalPersonService;


    public University getByEmail(String email) {
        Optional<University> university = repository.findByEmail(email);
        if (university.isEmpty()) {
            throw new NotFoundException(University.class);
        }
        return university.get();
    }


    public List<University> getByRepresentative(String email) {
        List<University> universities = repository.findAllByRepresentative(email);
        if (universities.isEmpty()) {
            throw new NotFoundException(JuridicalPerson.class);
        }
        return universities;
    }

    public List<PhysicalPerson> getAllRepresentatives(Long juridicalPersonId) {
        Optional<University> university = repository.findById(juridicalPersonId);
        return university.isPresent() ? university.get().getRepresentatives() : new ArrayList<>();
    }

}
