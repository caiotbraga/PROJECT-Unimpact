package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.Feedback;

import java.util.List;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findAllByStatusProject_Id(Long statusId);
}
