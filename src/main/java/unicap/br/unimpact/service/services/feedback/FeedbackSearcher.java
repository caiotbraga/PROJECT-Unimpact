package unicap.br.unimpact.service.services.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.domain.entities.Feedback;
import unicap.br.unimpact.repository.FeedbackRepository;

import java.util.List;

@Component
public class FeedbackSearcher {

    @Autowired
    FeedbackRepository repository;


    public List<Feedback> getAllByStatusId(Long statusId) {
        return this.repository.findAllByStatusProject_Id(statusId);
    }
}
