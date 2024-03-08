package unicap.br.unimpact.api.dtos.converter;

import org.springframework.core.convert.converter.Converter;
import unicap.br.unimpact.api.dtos.basic.ResponsibleTypeEnum;


public class StringToResponsibleTypeEnumConverter implements Converter<String, ResponsibleTypeEnum> {

    @Override
    public ResponsibleTypeEnum convert(String source) {
        return ResponsibleTypeEnum.valueOf(source.toUpperCase());
    }
}