package com.ecsolutions.cadastros.model.dtos.keycloak;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuth2TokenResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private Long expiresIn;

	@JsonProperty("refresh_expires_in")
	private Long refreshExpiresIn;

	@JsonProperty("refresh_token")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String refreshToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("not-before-policy")
	private Long notBeforePolicy;

	@JsonProperty("session_state")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sessionState;

	@JsonProperty("scope")
	private String scope;

}
