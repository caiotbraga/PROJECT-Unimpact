package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.MetadataProject;


@Repository
public interface MetadataProjectRepository extends JpaRepository<MetadataProject, Long> {

}
