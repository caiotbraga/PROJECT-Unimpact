package unicap.br.unimpact.api.dtos.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String street; 

    private String number;

    private String district;

    private String city;

    private String state;

    private String country;

    private String cep;
}
