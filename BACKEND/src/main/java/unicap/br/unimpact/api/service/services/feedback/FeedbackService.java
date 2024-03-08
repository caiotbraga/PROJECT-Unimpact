package unicap.br.unimpact.service.services.feedback;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.dtos.request.FeedbackRequest;
import unicap.br.unimpact.api.dtos.response.FeedbackResponse;
import unicap.br.unimpact.domain.entities.Feedback;
import unicap.br.unimpact.repository.FeedbackRepository;
import unicap.br.unimpact.service.interfaces.GenericCRUD;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackService extends GenericCRUD<Feedback, FeedbackResponse> {

    @Autowired
    private FeedbackRegister register;

    @Autowired
    private FeedbackSearcher searcher;

    @Autowired
    FeedbackRepository repository;


    @PostConstruct
    public void init() {
        this.init(Feedback.class, FeedbackResponse.class,
                this.repository, null);
    }

    public Feedback register(Long statusId, FeedbackRequest request) {
        return register.register(statusId, request);
    }

    public List<Feedback> getAllByStatusId(Long id) {
        return searcher.getAllByStatusId(id);
    }

}
