package unicap.br.unimpact.api.dtos.request.params.project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignToDepartmentParam {

    private Long projectId;
    private Long groupId;
}
