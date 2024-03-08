package unicap.br.unimpact.api.dtos.converter;

import org.springframework.core.convert.converter.Converter;
import unicap.br.unimpact.domain.enums.StatusEnum;


public class StringToStatusEnumConverter implements Converter<String, StatusEnum> {

    @Override
    public StatusEnum convert(String source) {
        return StatusEnum.valueOf(source.toUpperCase());
    }
}