package com.ecsolutions_erp.cadastros.model.attributes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ms")
@Data
public class MicroserviceAttributes {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static final class Service {
        private String name;
        private String url;
    }

}
