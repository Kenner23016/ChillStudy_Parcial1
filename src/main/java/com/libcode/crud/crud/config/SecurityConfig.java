package com.libcode.crud.crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthoritiesMapper customOidcUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()

                // 🔒 Rutas protegidas por el rol "Admin"
                .requestMatchers(
                    // Crear
                    "/estudiantes/nuevo", "/historial/nuevo",
                    "/cursos/nuevo", "/carreras/nuevo", "/intereses/nuevo",

                    // Editar
                    "/estudiantes/editar/**", "/historial/editar/**",
                    "/cursos/editar/**", "/carreras/editar/**", "/intereses/editar/**",

                    // Eliminar
                    "/estudiantes/eliminar/**", "/historial/eliminar/**",
                    "/cursos/eliminar/**", "/carreras/eliminar/**", "/intereses/eliminar/**"
                ).hasAuthority("Admin")

                // 🔓 Resto de rutas: requieren autenticación
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(userInfo -> userInfo
                    .oidcUserService(customOidcUserService) // ← mapeador de roles
                )
            )
            .logout(logout -> logout
                .logoutSuccessUrl("https://dev-w0qhs7x17m1xpogz.us.auth0.com/v2/logout?client_id=20x7hCr9qisvRBmlirv6muOirzAhY2aG&returnTo=https://chillstudy-parcial1.onrender.com")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}
