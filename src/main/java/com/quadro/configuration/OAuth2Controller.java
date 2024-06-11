package com.quadro.configuration;

import io.mzlnk.oauth2.exchange.core.authorizationcode.GoogleAuthorizationCodeExchange;
import io.mzlnk.oauth2.exchange.core.authorizationcode.response.dto.GoogleOAuth2TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class OAuth2Controller {

    private final GoogleAuthorizationCodeExchange googleExchange;

    public OAuth2Controller(GoogleAuthorizationCodeExchange googleExchange) {
        this.googleExchange = googleExchange;
    }

    @GetMapping("/callback/google")
    public ResponseEntity<TokenDetails> googleOAuth2Callback(@RequestParam String code) {
        var tokenResponse = googleExchange.exchangeAuthorizationCode(code);
        return ResponseEntity.ok(new TokenDetails((GoogleOAuth2TokenResponse) tokenResponse));
    }

    private static record TokenDetails(String accessToken, String idToken) {
        public TokenDetails(GoogleOAuth2TokenResponse tokenResponse) {
            this(tokenResponse.getAccessToken(), tokenResponse.get("id_token").toString());
        }
    }
}
