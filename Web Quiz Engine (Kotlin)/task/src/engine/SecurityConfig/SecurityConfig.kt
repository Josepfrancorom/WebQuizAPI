package engine.SecurityConfig

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/register").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/api/quizzes").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/api/quizzes").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/api/quizzes/**").hasRole("USER")
                    .requestMatchers(HttpMethod.DELETE, "/api/quizzes/**").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/api/quizzes/**").hasRole("USER")
                    .anyRequest().denyAll()
            }
            .httpBasic(Customizer.withDefaults()) // Si no necesitas autenticación básica, puedes eliminar esta línea
            .csrf { it.disable() } // Desactiva CSRF si no lo necesitas
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}