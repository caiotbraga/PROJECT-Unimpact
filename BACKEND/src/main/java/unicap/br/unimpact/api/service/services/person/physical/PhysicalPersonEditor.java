package unicap.br.unimpact.service.services.person.physical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.repository.PhysicalPersonRepository;

@Component
public class PhysicalPersonEditor {

    @Autowired
    PhysicalPersonRepository repository;


    public void addFunction(PhysicalPerson physicalPerson, FunctionEnum function) {
        if (!physicalPerson.getFunctions().contains(function.name())) {
            physicalPerson.getFunctions().add(function.name());
            repository.save(physicalPerson);
        }
    }


    public void removeFunction(PhysicalPerson physicalPerson, FunctionEnum function) {
        physicalPerson.getFunctions().remove(function.name());
        repository.save(physicalPerson);
    }

}
