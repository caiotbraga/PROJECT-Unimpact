package unicap.br.unimpact.api.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {

    private String ownerUserEmail;

    private String name;

    private String description;

    private List<String> areasOfOperation;
}
