package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.ProposalProject;


@Repository
public interface ProposalProjectRepository extends JpaRepository<ProposalProject, Long> {
    ProposalProject findAllByProject_Id(Long id);
}
