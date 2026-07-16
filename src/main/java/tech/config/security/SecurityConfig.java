package tech.config.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.service.security.AutenticacaoService;

import java.beans.BeanProperty;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final AutenticacaoService autenticacaoService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(SecurityFilter securityFilter, AutenticacaoService autenticacaoService, CustomAuthenticationEntryPoint authenticationEntryPoint) {
        this.securityFilter = securityFilter;
        this.autenticacaoService = autenticacaoService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider =  new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(autenticacaoService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; " +
                                        "script-src 'self'; " +
                                        "style-src 'self' 'unsafe-inline'; " +
                                        "connect-src 'self' http://localhost:4200 ws://localhost:4200;")
                        )
                )

                .authorizeHttpRequests(authorize -> authorize

                        // ARQUIVOS ESTÁTICOS (CSS)
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // CADASTRO E AUTENTICAÇÃO
                        .requestMatchers(HttpMethod.POST, "/api/cadastrar-usuario").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/cadastrar-admin").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/admin").permitAll()

                        // SWAGGER
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // DEMAIS ROTAS
                        .requestMatchers(HttpMethod.GET, "/api/status").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/main").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/main/editar/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/main/editar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/main/excluir/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/main/excluir").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/index").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/main_admin").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/register_admin").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/main_login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/admin").permitAll()

                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<SecurityFilter> registration(SecurityFilter filter) {
        FilterRegistrationBean<SecurityFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
}
