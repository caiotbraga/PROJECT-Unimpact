package unicap.br.unimpact.api.dtos.converter;

import org.springframework.core.convert.converter.Converter;
import unicap.br.unimpact.domain.enums.StateEnum;


public class StringToStateEnumConverter implements Converter<String, StateEnum> {

    @Override
    public StateEnum convert(String source) {
        return StateEnum.valueOf(source.toUpperCase());
    }
}