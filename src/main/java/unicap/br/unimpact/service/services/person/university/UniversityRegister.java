package unicap.br.unimpact.service.services.person.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.entities.University;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.repository.UniversityRepository;
import unicap.br.unimpact.service.services.person.physical.PhysicalPersonService;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class UniversityRegister {

    @Autowired
    UniversityRepository repository;

    @Autowired
    PhysicalPersonService physicalPersonService;


    public void addRepresentative(University university, String recipient) {
        PhysicalPerson physicalPerson = physicalPersonService.getByEmail(recipient);

        if (Objects.isNull(university.getRepresentatives()))
            university.setRepresentatives(new ArrayList<>());

        university.getRepresentatives().add(physicalPerson);
        physicalPersonService.addFunction(physicalPerson, FunctionEnum.ROLE_MIDDLE_EMPLOYEE);

        this.repository.save(university);
    }

}
