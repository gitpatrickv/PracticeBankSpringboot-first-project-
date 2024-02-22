package com.springboot.practicebank.config;

import com.springboot.practicebank.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import static com.springboot.practicebank.entity.constants.Role.ADMIN;
import static com.springboot.practicebank.entity.constants.Role.BANKER;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.PUT, "/api/account/freeze").hasAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/account/delete").hasAuthority(ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/api/account/credit").hasAnyAuthority(ADMIN.name(), BANKER.name())
                                .requestMatchers(HttpMethod.PUT, "/api/account/updateInfo").hasAnyAuthority(ADMIN.name(), BANKER.name())
                                .requestMatchers("/api/account/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/user/register").hasAnyAuthority(ADMIN.name(), BANKER.name())
                                .requestMatchers("/api/user/**").permitAll()
                                .requestMatchers("/api/transactions/**").authenticated()
                                .anyRequest().authenticated()
                );

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authenticationProvider(authenticationProvider);
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.logout(
                logout -> logout.logoutUrl("/api/user/logout")
                        .addLogoutHandler(new SecurityContextLogoutHandler())
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        );

        return httpSecurity.build();
    }
}
