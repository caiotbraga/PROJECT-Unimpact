package unicap.br.unimpact.service.services.person.juridical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.JuridicalPerson;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.domain.validators.PasswordValidator;
import unicap.br.unimpact.repository.JuridicalPersonRepository;
import unicap.br.unimpact.service.interfaces.PreCondition;
import unicap.br.unimpact.service.exceptions.business.AlreadyExistsException;
import unicap.br.unimpact.service.exceptions.business.InvalidFieldException;
import unicap.br.unimpact.service.services.others.AuthService;

import java.util.ArrayList;
import java.util.List;


@Component
public class JuridicalPersonPreCondition implements PreCondition<JuridicalPerson> {

    @Autowired
    JuridicalPersonRepository repository;

    @Autowired
    AuthService authService;

    @Override
    public void preRegister(JuridicalPerson entity) {
        if (repository.existsByCnpj(entity.getCnpj())) {
            throw new AlreadyExistsException("cnpj");
        }

        if (repository.existsByEmail(entity.getEmail())) {
            throw new AlreadyExistsException("email");
        }

        PasswordValidator passwordValidator = new PasswordValidator();
        if (!passwordValidator.isValid(entity.getPassword())) {
            throw new InvalidFieldException("password");
        }

        authService.encryptPassword(entity);

        entity.setFunctions(new ArrayList<>(List.of(FunctionEnum.ROLE_REQUESTING_MANAGER.name())));
    }

    @Override
    public void preEdit(JuridicalPerson entity) {
        authService.encryptPassword(entity);
    }

    @Override
    public void preDelete(JuridicalPerson entity) {
        entity.setRepresentatives(new ArrayList<>());
        repository.save(entity);
    }
}
