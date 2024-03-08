package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.StatusProject;

import java.util.List;


@Repository
public interface StatusProjectRepository extends JpaRepository<StatusProject, Long> {

    List<StatusProject> getAllByState_Id(Long stateId);
}
