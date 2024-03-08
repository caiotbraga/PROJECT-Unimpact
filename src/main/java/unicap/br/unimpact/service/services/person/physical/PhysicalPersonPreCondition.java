package unicap.br.unimpact.service.services.person.physical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.enums.FunctionEnum;
import unicap.br.unimpact.domain.validators.PasswordValidator;
import unicap.br.unimpact.repository.PhysicalPersonRepository;
import unicap.br.unimpact.service.interfaces.PreCondition;
import unicap.br.unimpact.service.exceptions.business.AlreadyExistsException;
import unicap.br.unimpact.service.exceptions.business.InvalidFieldException;
import unicap.br.unimpact.service.services.others.AuthService;

import java.util.ArrayList;
import java.util.List;


@Component
public class PhysicalPersonPreCondition implements PreCondition<PhysicalPerson> {

    @Autowired
    PhysicalPersonRepository repository;

    @Autowired
    AuthService authService;

    @Override
    public void preRegister(PhysicalPerson entity) {
        if (repository.existsByCpf(entity.getCpf())) {
            throw new AlreadyExistsException("cpf");
        }

        if (repository.existsByEmail(entity.getEmail())) {
            throw new AlreadyExistsException("email");
        }

        PasswordValidator passwordValidator = new PasswordValidator();
        if (!passwordValidator.isValid(entity.getPassword())) {
            throw new InvalidFieldException("password");
        }

        authService.encryptPassword(entity);

        entity.setFunctions(new ArrayList<>(List.of(FunctionEnum.ROLE_REQUESTING_SIMPLE.name())));
    }

    @Override
    public void preEdit(PhysicalPerson entity) {
        authService.encryptPassword(entity);
    }

    @Override
    public void preDelete(PhysicalPerson entity) {
        // pass
    }
}
