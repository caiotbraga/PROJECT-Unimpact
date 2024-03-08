package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.JuridicalPerson;

import java.util.List;
import java.util.Optional;

@Repository
public interface JuridicalPersonRepository extends JpaRepository<JuridicalPerson, Long> {
    Optional<JuridicalPerson> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCnpj(String cnpj);

    @Query(nativeQuery = true, value = "select distinct * from juridical_person j " +
            "join juridical_person_representatives jr on jr.juridical_person_id = j.id " +
            "join physical_person p on p.id = jr.representatives_id " +
            "where p.email =:representativeEmail")
    List<JuridicalPerson> findAllByRepresentative(String representativeEmail);
}
