package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.Responsible;


@Repository
public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {

}
