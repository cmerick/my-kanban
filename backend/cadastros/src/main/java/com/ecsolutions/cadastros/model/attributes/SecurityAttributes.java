package com.ecsolutions.cadastros.model.attributes;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.oauth")
@Data
public class SecurityAttributes {

	private String url;

	private String realm;

	private String[] roleMapKeys;

	private String clientIdUsers;

	private String clientId;

	private String clientSecret;

	private String granType;


}
