package br.com.lucotavio.restspringboot.converter;

import br.com.lucotavio.restspringboot.configuration.WebConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class YamlJackson2TttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    public YamlJackson2TttpMessageConverter() {
        super(new YAMLMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL),
                MediaType.parseMediaType(WebConfig.APPLICATION_YML.toString()));
    }
}
