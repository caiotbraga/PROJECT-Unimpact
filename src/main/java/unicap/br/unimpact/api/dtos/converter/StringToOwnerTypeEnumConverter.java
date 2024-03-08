package unicap.br.unimpact.api.dtos.converter;

import org.springframework.core.convert.converter.Converter;
import unicap.br.unimpact.api.dtos.basic.OwnerTypeEnum;


public class StringToOwnerTypeEnumConverter implements Converter<String, OwnerTypeEnum> {

    @Override
    public OwnerTypeEnum convert(String source) {
        return OwnerTypeEnum.valueOf(source.toUpperCase());
    }
}