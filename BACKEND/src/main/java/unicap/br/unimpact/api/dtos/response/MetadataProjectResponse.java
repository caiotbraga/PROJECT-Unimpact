package unicap.br.unimpact.api.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadataProjectResponse extends DTOResponse {

    private Long id;

    private String title;

    private String summary;

    private Date startDate;

    private Date createdAt;

    private Date modifiedAt;

    private Date finishDate;

    private List<String> targetPublic;

    private Integer numberBeneficiaries;

    private String mainObjective;

    private List<String> specificObjectives;

    private String moreDetails;
}
