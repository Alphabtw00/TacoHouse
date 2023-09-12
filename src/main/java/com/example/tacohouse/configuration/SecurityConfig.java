package com.example.tacohouse.configuration;

import com.example.tacohouse.model.User;
import com.example.tacohouse.repositories.UserRepository;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username->{
            User user = userRepository.findByUsername(username);
            if(user!=null){
                return user;
            }
            throw new UsernameNotFoundException("User:\"" + username + "\" not found" );
        };
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers(("/design"), ("/orders"), ("/orders/current"))
                        .hasAnyRole("USER","ADMIN")
                        .requestMatchers(("/"),("/**")).permitAll()
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                        .requestMatchers(toH2Console()).permitAll())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form->
                        form.loginPage("/login")
                                .defaultSuccessUrl(("/"),true))
                .logout(logout->
                        logout.logoutSuccessUrl("/login"));
        return http.build();
    }
}
