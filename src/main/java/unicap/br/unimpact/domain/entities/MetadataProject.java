package unicap.br.unimpact.domain.entities;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MetadataProject extends BaseEntity {

    @NotNull
    @Size(max = 500)
    private String title;

    @NotNull
    @Size(max = 50000)
    private String summary;

    private Date startDate;

    private Date finishDate;

    @ElementCollection
    private List<String> targetPublic;

    private Integer numberBeneficiaries;

    @NotNull
    @Size(max = 1000)
    private String mainObjective;

    @NotNull
    @ElementCollection
    private List<String> specificObjectives;

    private String assignedEmail;

    @Size(max = 50000)
    private String moreDetails;

    @ElementCollection
    private List<String> areasOfOperation;
}
