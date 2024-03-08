package unicap.br.unimpact.service.services.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unicap.br.unimpact.api.dtos.request.FeedbackRequest;
import unicap.br.unimpact.domain.entities.Feedback;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.repository.FeedbackRepository;
import unicap.br.unimpact.service.auxiliary.EntityValidator;
import unicap.br.unimpact.service.auxiliary.ModelMap;
import unicap.br.unimpact.service.services.others.AuthService;
import unicap.br.unimpact.service.services.project.status.StatusProjectService;

@Component
public class FeedbackRegister {

    @Autowired
    FeedbackRepository repository;

    @Autowired
    StatusProjectService statusProjectService;

    @Autowired
    AuthService authService;


    public Feedback register(Long statusId, FeedbackRequest request) {
        Feedback feedback = new Feedback();
        User actualUser = this.authService.getCurrentUser();

        ModelMap.map(request, feedback);
        feedback.setSenderEmail(actualUser.getEmail());
        feedback.setSenderName(actualUser.getName());
        feedback.setStatusProject(this.statusProjectService.get(statusId));
        EntityValidator.validate(feedback);

        return this.repository.save(feedback);
    }
}
