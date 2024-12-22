package az.developia.BalanceApp.trim;


import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Конфигуратор для регистрации кастомного десериализатора Jackson.
 */
@Configuration
public class OurStringTrimmingConverter implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Создаем новый модуль Jackson и регистрируем в нем наш десериализатор
        SimpleModule stringTrimmingModule = new SimpleModule();
        stringTrimmingModule.addDeserializer(String.class, new StringTrimmingDeserializer());

        // Ищем конвертер Jackson и добавляем в него модуль
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jacksonConverter =
                        (MappingJackson2HttpMessageConverter) converter;
                ObjectMapper objectMapper = jacksonConverter.getObjectMapper();
                objectMapper.registerModule(stringTrimmingModule);
            }
        }
    }
}
