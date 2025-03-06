package com.example.tacohouse.configuration;

import com.example.tacohouse.entities.User;
import com.example.tacohouse.repositories.UserRepository;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity //to disable basic security and configure your own
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){ //passowrd encrypter bean
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){ //spring uses this whenever someone tries logging in
        return username->{                                                       //userDetailService interface has single method (loadByUsername) which returns UserDetails object, so we implement it as a lambda (functional interface)
            User user = userRepository.findByUsername(username);
            if(user!=null){
                return user;
            }
            throw new UsernameNotFoundException("User:\"" + username + "\" not found" );
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception{ //used a full mvc request builder so all controller requests can be used using this. Controller requests can be used default too but if we have a servlet other than dispatcher running too, to secure it we need to build our own mvc requests like this.
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
        http
                .authorizeHttpRequests((auth)-> auth

                        .requestMatchers(                                                // (done first)
                                mvc.pattern("/home"),
                                mvc.pattern("/images/**"), //loading any images need this
                                mvc.pattern("/login"),     //login and register permitted be default if not using
                                mvc.pattern("register"),
                                mvc.pattern("/")
                        ).permitAll()


                        .requestMatchers(                                              //(should be done second)
                                mvc.pattern("/taco/design")
                                ,mvc.pattern("/orders") //
                                ,mvc.pattern("/orders/current")
                                ,mvc.pattern("/taco/menu")
                                ,mvc.pattern("/profile")
                                ,mvc.pattern("/profile/edit")
                                ,mvc.pattern("/contact")
                        ).hasAnyRole("USER","ADMIN")


                        .requestMatchers(
                                mvc.pattern("/admin")
                        ).hasRole("ADMIN")


                        .requestMatchers(
                                EndpointRequest.toAnyEndpoint()
                        ).hasRole("ADMIN") //to secure all endpoints including actuator endpoints


                        .anyRequest().authenticated() //all requests are authenticated     (should be done last)
                )

                .formLogin(form-> form
                        .loginPage("/login")   //redirect users to this page
                        .loginProcessingUrl("/login") // post req to this url starts the login process
                        .failureUrl("/login?error") //if failed redirect to this url
                        .defaultSuccessUrl(("/home"),true)) //redirects to this url. if user was on another url before login it still directs to this (if alwaysUse is true)
                
                .logout(logout-> logout
                        .logoutUrl("/logout") //send req to this to tell spring securiy to logout
                        .logoutSuccessUrl("/login?logout").permitAll()); //have to permit if custom success url (does this same by default anyways)

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { //as h2 console url is not controlled by spring mvc(uses a different servlet), if we have spring mvc on classpath and are securing other servlet than dispatcher servlet which is default, we need to use introspector and mvcrequestbuilder), it uses ant matchers for that and we cant have both antMatchers and MVC
        return (web) -> web.ignoring().requestMatchers(antMatcher("/h2-console/**"));
    }
}
