package com.williams.springsecuritycifrado.security.config;

import com.williams.springsecuritycifrado.entities.util.Role;
import com.williams.springsecuritycifrado.security.jwt.filter.JwtAuthenticationFilter;
import com.williams.springsecuritycifrado.entities.util.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase para la configuración de seguridad Spring Security
 */
@Configuration
@EnableWebSecurity // permite a Spring aplicar esta configuracion a la configuraicon de seguridad global
public class SecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement( sessionMangaConfig -> sessionMangaConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> {
                            authorize.requestMatchers("/api/hello").permitAll();
                            authorize.requestMatchers("/api/bye").permitAll();
                            authorize.requestMatchers("/api/auth/**").permitAll();
                            authorize.requestMatchers(HttpMethod.GET,"/api/cars/**").hasAuthority(Permission.READ_CARS.name());
                            authorize.requestMatchers(HttpMethod.POST,"/api/cars/**").hasAuthority(Permission.SAVE_CAR.name());
                            authorize.requestMatchers(HttpMethod.PUT,"/api/cars/**").hasAuthority(Permission.UPDATE_CAR.name());
                            authorize.requestMatchers(HttpMethod.DELETE,"/api/cars/**").hasRole(Role.ADMINISTRATOR.name());
                            authorize.requestMatchers(HttpMethod.GET,"/user/**").hasAuthority(Permission.READ_USER.name());
                            authorize.requestMatchers(HttpMethod.PUT,"/user/permission/**").hasRole(Role.ADMINISTRATOR.name());
                            authorize.requestMatchers(HttpMethod.DELETE,"/user/**").hasRole(Role.ADMINISTRATOR.name());
                            authorize.anyRequest().authenticated();
                        }
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

}
