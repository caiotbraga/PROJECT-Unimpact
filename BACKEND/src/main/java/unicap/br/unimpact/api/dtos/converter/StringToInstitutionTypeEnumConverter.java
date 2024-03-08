package unicap.br.unimpact.api.dtos.converter;

import org.springframework.core.convert.converter.Converter;
import unicap.br.unimpact.domain.enums.InstitutionTypeEnum;


public class StringToInstitutionTypeEnumConverter implements Converter<String, InstitutionTypeEnum> {

    @Override
    public InstitutionTypeEnum convert(String source) {
        return InstitutionTypeEnum.valueOf(source.toUpperCase());
    }
}