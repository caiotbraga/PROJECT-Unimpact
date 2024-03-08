package unicap.br.unimpact.api.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    private String title;

    private String summary;

    private List<String> targetPublic;

    private Integer numberBeneficiaries;

    private String mainObjective;

    private List<String> specificObjectives;

    private String moreDetails;

    private List<String> areasOfOperation;

    private Date startDate;

    private Date finishDate;
}
