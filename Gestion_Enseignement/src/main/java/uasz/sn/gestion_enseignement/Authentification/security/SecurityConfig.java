package uasz.sn.gestion_enseignement.Authentification.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] For_Permanent = {"/Permanent/**"};
    private static final String[] For_Vacataire = {"/Vacataire/**"};
    private static final String[] For_ChefDepartement = {"/ChefDepartement/**"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/js/**","/css/**").permitAll()
                                .requestMatchers("/login**","/logout**").permitAll()
                                .requestMatchers("/h2/**").permitAll()

                                .requestMatchers(For_Vacataire).hasRole("Vacataire")
                                .requestMatchers(For_Permanent).hasRole("Permanent")
                                .requestMatchers(For_ChefDepartement).hasRole("ChefDepartement")
                                .anyRequest().authenticated()
                )
                .formLogin((formLogin) ->
                        formLogin
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginPage("/login")
                                .defaultSuccessUrl("/")
                                .successForwardUrl("/")
                                .permitAll()
                );
        return http.build();
    }
}