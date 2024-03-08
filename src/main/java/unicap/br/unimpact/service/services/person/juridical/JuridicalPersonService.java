package unicap.br.unimpact.service.services.person.juridical;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.response.JuridicalPersonResponse;
import unicap.br.unimpact.domain.entities.JuridicalPerson;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.repository.JuridicalPersonRepository;
import unicap.br.unimpact.service.interfaces.GenericCRUD;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class JuridicalPersonService extends GenericCRUD<JuridicalPerson, JuridicalPersonResponse> {

    @Autowired
    JuridicalPersonRepository repository;

    @Autowired
    JuridicalPersonPreCondition preCondition;

    @Autowired
    JuridicalPersonRegister register;

    @Autowired
    JuridicalPersonRemover remover;

    @Autowired
    JuridicalPersonSearcher searcher;

    @PostConstruct
    public void init() {
        this.init(JuridicalPerson.class, JuridicalPersonResponse.class,
                this.repository, this.preCondition);
    }

    public JuridicalPerson getByEmail(String email) {
        return searcher.getByEmail(email);
    }

    public List<JuridicalPerson> getByRepresentative(String email) {
        return searcher.getByRepresentative(email);
    }

    public void addRepresentative(JuridicalPerson juridicalPerson, String recipient) {
        register.registerNewRepresentative(juridicalPerson, recipient);
    }

    public List<PhysicalPerson> getAllRepresentatives(Long juridicalPersonId) {
        return searcher.getAllRepresentatives(juridicalPersonId);
    }

    public void removeRepresentative(Long juridicalPersonId, String recipient) {
        remover.removeRepresentative(juridicalPersonId, recipient);
    }

}
