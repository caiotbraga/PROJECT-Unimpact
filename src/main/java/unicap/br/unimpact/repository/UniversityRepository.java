package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.University;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    Optional<University> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCnpj(String cnpj);

    @Query(nativeQuery = true, value = "select distinct * from university j " +
            "join university_representatives jr on jr.university_id = j.id " +
            "join physical_person p on p.id = jr.representatives_id " +
            "where p.email =:representativeEmail")
    List<University> findAllByRepresentative(String representativeEmail);
}
