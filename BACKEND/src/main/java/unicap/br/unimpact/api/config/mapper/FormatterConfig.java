package unicap.br.unimpact.api.config.mapper;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import unicap.br.unimpact.api.dtos.converter.*;

@Configuration
public class FormatterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToInstitutionTypeEnumConverter());
        registry.addConverter(new StringToNotificationTypeEnumConverter());
        registry.addConverter(new StringToOwnerTypeEnumConverter());
        registry.addConverter(new StringToResponsibleTypeEnumConverter());
        registry.addConverter(new StringToStateEnumConverter());
        registry.addConverter(new StringToStatusEnumConverter());
    }
}
