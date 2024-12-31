package com.ms19.oauth2Demo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain CustomeSecurityFilterChain(HttpSecurity http) throws  Exception{
       return http.authorizeHttpRequests(auth->auth
                       .requestMatchers("api/v1/").authenticated()
//                       .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().permitAll())
                .csrf(cs->cs.disable())
                .oauth2Login(oauth->oauth
//                .defaultSuccessUrl("http://localhost:8080/success",true))
                .defaultSuccessUrl("http://localhost:5173/dashboard",true))
               .build();



    }


}
