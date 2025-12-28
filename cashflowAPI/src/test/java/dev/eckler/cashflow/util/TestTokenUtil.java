package dev.eckler.cashflow.util;

import static java.util.Collections.singletonList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.oauth2.server.resource.autoconfigure.OAuth2ResourceServerProperties;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.eckler.cashflow.model.TestJwtToken;

@TestComponent
public class TestTokenUtil {

    static String CLIENT_ID = "cashflow";
    static String CLIENT_SECRET = "T9zHMs2YRIgy5mMckbBNALo1URv7Dp55";

    @Autowired
    OAuth2ResourceServerProperties oAuth2Properties;

    @Bean
    public TestJwtToken testJwtToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("grant_type", singletonList("client_credentials"));
        map.put("client_id", singletonList(CLIENT_ID));
        map.put("client_secret", singletonList(CLIENT_SECRET));

        String authServerUrl = oAuth2Properties.getJwt().getIssuerUri() +
                "/protocol/openid-connect/token";

        var request = new HttpEntity<>(map, httpHeaders);
        KeyCloakToken token = restTemplate.postForObject(
                authServerUrl,
                request,
                KeyCloakToken.class);

        assert token != null;
        return new TestJwtToken(token.accessToken());
    }

    record KeyCloakToken(@JsonProperty("access_token") String accessToken) {
    }

}
