package unicap.br.unimpact.api.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse extends DTOResponse {

    private Long id;

    private String ownerEmail;

    private ResponsibleResponse responsible;

    private MetadataProjectResponse metadataProject;

    private StateProjectResponse actualState;

    private Date createdAt;

    private Date modifiedAt;

    private boolean isNew;

}
