package unicap.br.unimpact.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import unicap.br.unimpact.domain.annotations.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public abstract class User extends BaseEntity {

    @NotNull(message = "Name is mandatory")
    private String name;

    @Email
    @NotNull(message = "Email is mandatory")
    private String email;

    @NotNull(message = "Password is mandatory")
    @JsonIgnore
    @ToString.Exclude
    private String password;

    @ElementCollection
    private List<String> functions;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Phone> phones;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Address> addresses;
}