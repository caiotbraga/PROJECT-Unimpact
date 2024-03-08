package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.StateProject;

import java.util.List;


@Repository
public interface StateProjectRepository extends JpaRepository<StateProject, Long> {

    List<StateProject> getAllByProject_Id(Long projectId);
}
