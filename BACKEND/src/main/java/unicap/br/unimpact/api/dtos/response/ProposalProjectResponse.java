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
public class ProposalProjectResponse extends DTOResponse {

    private Long id;

    private String ownerEmail;

    private String description;

    private Date createdAt;

    private Date modifiedAt;

}
