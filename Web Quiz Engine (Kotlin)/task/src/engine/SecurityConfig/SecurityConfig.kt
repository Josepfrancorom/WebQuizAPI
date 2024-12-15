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
                auth.anyRequest().permitAll() // Permite acceso a cualquier solicitud
            }
            .httpBasic(Customizer.withDefaults()) // Si no necesitas autenticación básica, puedes eliminar esta línea
            .csrf { it.disable() } // Desactiva CSRF si no lo necesitas
            .build()
    }
}