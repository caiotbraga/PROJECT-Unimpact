package unicap.br.unimpact.domain.entities;

import lombok.*;

import javax.persistence.Entity;


@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Phone extends BaseEntity {

    private String number;
}
