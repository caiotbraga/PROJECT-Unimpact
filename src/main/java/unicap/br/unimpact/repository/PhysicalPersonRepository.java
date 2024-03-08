package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.PhysicalPerson;

import java.util.Optional;

@Repository
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPerson, Long> {
    Optional<PhysicalPerson> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
