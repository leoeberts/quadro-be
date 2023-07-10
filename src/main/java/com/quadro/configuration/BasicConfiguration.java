package com.quadro.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class BasicConfiguration {

    //TODO remove basic auth
    @Bean
    public InMemoryUserDetailsManager userDetailsService(@Value("${auth-user.name}") String username,
                                                         @Value("${auth-user.password}") String userPassword,
                                                         PasswordEncoder passwordEncoder
    ) {
        UserDetails user = User.withUsername(username)
                .password(passwordEncoder.encode(userPassword))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //TODO enable and configure
                .authorizeHttpRequests((auth) -> auth
                        .anyRequest().authenticated())
                .httpBasic(withDefaults())
                .requiresChannel((requiresChannel) ->
                                         requiresChannel.requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                                                 .requiresSecure());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}
