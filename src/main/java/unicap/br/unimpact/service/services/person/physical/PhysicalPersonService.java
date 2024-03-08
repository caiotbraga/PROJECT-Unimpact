package unicap.br.unimpact.service.services.person.physical;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.response.PhysicalPersonResponse;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.repository.PhysicalPersonRepository;
import unicap.br.unimpact.service.interfaces.GenericCRUD;

import javax.annotation.PostConstruct;


@Service
@Slf4j
@AllArgsConstructor
public class PhysicalPersonService extends GenericCRUD<PhysicalPerson, PhysicalPersonResponse> {

    @Autowired
    PhysicalPersonRepository repository;

    @Autowired
    PhysicalPersonPreCondition preCondition;

    @Autowired
    PhysicalPersonSearcher searcher;

    @Autowired
    PhysicalPersonEditor editor;


    @PostConstruct
    public void init() {
        this.init(PhysicalPerson.class, PhysicalPersonResponse.class,
                this.repository, this.preCondition);
    }


    public PhysicalPerson getByEmail(String email) {
        return searcher.getByEmail(email);
    }

    public void addFunction(PhysicalPerson physicalPerson, FunctionEnum function) {
        editor.addFunction(physicalPerson, function);
    }

    public void removeFunction(PhysicalPerson physicalPerson, FunctionEnum function) {
        editor.removeFunction(physicalPerson, function);
    }
}
