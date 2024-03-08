package unicap.br.unimpact.api.dtos.request.params.project;

import lombok.Getter;
import lombok.Setter;
import unicap.br.unimpact.api.dtos.basic.OwnerTypeEnum;
import unicap.br.unimpact.api.dtos.basic.ResponsibleTypeEnum;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class RegisterProjectParam {

    private OwnerTypeEnum ownerType;

    private String ownerIdentification;

    private String title;

    private String summary;

    private List<String> targetPublic;

    private Integer numberBeneficiaries;

    private String mainObjective;

    private String moreDetails;

    private List<String> specificObjectives;

    private Date startDate;

    private Date finishDate;
}
