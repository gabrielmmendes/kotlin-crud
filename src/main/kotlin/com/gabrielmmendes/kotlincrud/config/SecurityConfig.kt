package com.gabrielmmendes.kotlincrud.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
//                    .requestMatchers("/swagger-ui.html", "/swagger-ui/*", "/v3/**").permitAll()
                    .anyRequest().permitAll()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwtConfigurer ->  // Nova forma de configurar JWT
                    jwtConfigurer.jwkSetUri("http://localhost:8080/realms/sobrevidas/protocol/openid-connect/certs")
                }
            }
            .csrf { it.disable() } // Nova forma de desativar CSRF

        return http.build()
    }

    @Bean
    fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
    }
}
