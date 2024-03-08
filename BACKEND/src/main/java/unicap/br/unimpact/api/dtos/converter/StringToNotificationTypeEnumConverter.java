package unicap.br.unimpact.api.dtos.converter;

import org.springframework.core.convert.converter.Converter;
import unicap.br.unimpact.domain.enums.NotificationTypeEnum;


public class StringToNotificationTypeEnumConverter implements Converter<String, NotificationTypeEnum> {

    @Override
    public NotificationTypeEnum convert(String source) {
        return NotificationTypeEnum.valueOf(source.toUpperCase());
    }
}