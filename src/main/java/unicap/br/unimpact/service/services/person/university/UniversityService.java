package unicap.br.unimpact.service.services.person.university;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.response.UniversityResponse;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.entities.University;
import unicap.br.unimpact.repository.UniversityRepository;
import unicap.br.unimpact.service.interfaces.GenericCRUD;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class UniversityService extends GenericCRUD<University, UniversityResponse> {

    @Autowired
    UniversityRepository repository;

    @Autowired
    UniversityPreCondition preCondition;

    @Autowired
    UniversityRegister register;

    @Autowired
    UniversityRemover remover;

    @Autowired
    UniversitySearcher searcher;


    @PostConstruct
    public void init() {
        this.init(University.class, UniversityResponse.class,
                this.repository, this.preCondition);
    }

    public void addRepresentative(University university, String recipient) {
        register.addRepresentative(university, recipient);
    }

    public University getByEmail(String email) {
        return searcher.getByEmail(email);
    }

    public List<University> getByRepresentative(String email) {
        return searcher.getByRepresentative(email);
    }

    public List<PhysicalPerson> getAllRepresentatives(Long id) {
        return searcher.getAllRepresentatives(id);
    }

    public void removeRepresentative(Long universityId, String recipient) {
        remover.removeRepresentative(universityId, recipient);
    }

}
