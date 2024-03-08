package unicap.br.unimpact.service.services.person.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.entities.University;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.repository.UniversityRepository;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

@Component
public class UniversityRemover {

    @Autowired
    UniversityRepository repository;

    @Autowired
    PhysicalPersonService physicalPersonService;


    public void removeRepresentative(Long universityId, String recipient) {
        University university = repository.getById(universityId);
        PhysicalPerson physicalPerson = physicalPersonService.getByEmail(recipient);

        university.getRepresentatives().remove(physicalPerson);
        physicalPersonService.removeFunction(physicalPerson, FunctionEnum.ROLE_MIDDLE_EMPLOYEE);

        this.repository.save(university);
    }

}
