package dev.profitsoft.internship.rebrov.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

        @Bean
        public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            RedirectServerAuthenticationSuccessHandler successHandler =
                    new RedirectServerAuthenticationSuccessHandler("/");
            RedirectServerLogoutSuccessHandler logoutHandler = new RedirectServerLogoutSuccessHandler();
            return http
                    .csrf(ServerHttpSecurity.CsrfSpec::disable)
                    .cors(ServerHttpSecurity.CorsSpec::disable)
                    .authorizeExchange(exchanges -> exchanges
                            .pathMatchers("/login/**", "/oauth2/**", "/error").permitAll()
                            .pathMatchers("/", "/index.html", "/static/**", "/*.js", "/*.css", "/*.ico").permitAll()
                            .pathMatchers("/api/**").authenticated()
                            .anyExchange().permitAll()
                    )
                    .oauth2Login(oauth2 -> oauth2
                            .authenticationSuccessHandler(successHandler)
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessHandler(logoutHandler)
                    )
                    .exceptionHandling(exception -> exception
                            .authenticationEntryPoint(new RedirectServerAuthenticationEntryPoint("/login"))
                    )
                    .build();
        }
}

