package unicap.br.unimpact.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import unicap.br.unimpact.domain.enums.StateEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class StateProject extends BaseEntity {

    @OneToOne
    @JsonIgnore
    @ToString.Exclude
    private Project project;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StateEnum state;

    @OneToOne
    @JsonIgnore
    @ToString.Exclude
    private StatusProject actualStatus;

    @OneToMany
    @ToString.Exclude
    private List<StatusProject> status;
}
