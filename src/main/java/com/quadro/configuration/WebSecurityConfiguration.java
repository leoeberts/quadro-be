package com.quadro.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.EnableDefaultOAuth2Exchange;
import io.mzlnk.oauth2.exchange.springboot.autoconfigure.OAuth2ExchangeCoreAutoConfiguration;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableDefaultOAuth2Exchange
public class WebSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api-docs/**").permitAll()
                        .requestMatchers("/api/v1/auth/callback/**").permitAll()
                        .anyRequest().fullyAuthenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .requiresChannel((requiresChannel) ->
                                         requiresChannel.requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                                                 .requiresSecure());
        return http.build();
    }

    private final Logger log = LoggerFactory.getLogger(OAuth2ExchangeCoreAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient() {
        log.debug("No {} bean found - creating one", OkHttpClient.class.getName());
        return new OkHttpClient();
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        log.debug("No {} bean found - creating one", ObjectMapper.class.getName());
        return new ObjectMapper();
    }

}


