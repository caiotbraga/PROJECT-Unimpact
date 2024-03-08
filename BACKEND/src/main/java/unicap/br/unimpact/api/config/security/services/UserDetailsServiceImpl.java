package unicap.br.unimpact.api.config.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicap.br.unimpact.domain.entities.JuridicalPerson;
import unicap.br.unimpact.domain.entities.PhysicalPerson;
import unicap.br.unimpact.domain.entities.University;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.repository.JuridicalPersonRepository;
import unicap.br.unimpact.repository.PhysicalPersonRepository;
import unicap.br.unimpact.repository.UniversityRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PhysicalPersonRepository physicalPersonRepository;

    @Autowired
    JuridicalPersonRepository juridicalPersonRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        Optional<PhysicalPerson> personOptional = physicalPersonRepository.findByEmail(email);
        Optional<JuridicalPerson> juridicalPerson = juridicalPersonRepository.findByEmail(email);
        Optional<University> university = universityRepository.findByEmail(email);

        if (personOptional.isPresent()) {
            user = personOptional.get();
        } else if (juridicalPerson.isPresent()) {
            user = juridicalPerson.get();
        } else if (university.isPresent()) {
            user = university.get();
        }

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }

        return UserDetailsImpl.build(user);
    }

}
