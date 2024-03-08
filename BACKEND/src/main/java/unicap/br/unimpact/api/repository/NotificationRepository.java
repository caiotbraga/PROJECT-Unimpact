package unicap.br.unimpact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicap.br.unimpact.domain.entities.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> getAllByRecipientEmailAndIsVisible(String email, boolean isVisible);

    List<Notification> getAllBySenderEmailAndIsVisible(String email, boolean isVisible);
}
