package unicap.br.unimpact.api.dtos.request.params.project;

import lombok.Getter;
import lombok.Setter;
import unicap.br.unimpact.api.dtos.basic.OwnerTypeEnum;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class RegisterProjectGroupParam {

    private OwnerTypeEnum ownerType;

    private String ownerIdentification;

    private List<Long> groupIds;

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
