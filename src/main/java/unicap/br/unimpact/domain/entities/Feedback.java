package unicap.br.unimpact.domain.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends BaseEntity {

    @ManyToOne
    private StatusProject statusProject;

    private String senderEmail;

    private String senderName;

    @Size(max = 300)
    private String subject;

    @Size(max = 50000)
    private String message;

}
