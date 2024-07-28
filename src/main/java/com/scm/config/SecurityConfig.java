package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsDervice userDetailsDervice;
    
    @Autowired
    private OAuthAuthenticationSuccessHandle oauthHandler;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // need to create Userdetailsservice object
        daoAuthenticationProvider.setUserDetailsService(userDetailsDervice);
        // need password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // to restrict and open urls
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/", "/signup", "/login", "/css/**", "/images/**",
            // "/script/**").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        
       // for custom login form
        httpSecurity.formLogin( formLogin -> {
        	formLogin.loginPage("/login");
        	formLogin.loginProcessingUrl("/authenticate");
        	formLogin.successForwardUrl("/user/dashboard");
//        	formLogin.failureForwardUrl("/login?error=true");
        	formLogin.usernameParameter("email");
        	formLogin.passwordParameter("password");
        } );
        
        
        
       // Required to hit get request on logout url otherwise need to sent post request on logout url
        httpSecurity.csrf( AbstractHttpConfigurer::disable) ;
        
        httpSecurity.logout(logoutForm -> {
        	logoutForm.logoutUrl("/do-logout");
        	logoutForm.logoutSuccessUrl("/login?logout=true");
        });
        
        
        
        
        
        // for default login page of spring boot
        //httpSecurity.formLogin(Customizer.withDefaults());

        
        //oauth default login
        //httpSecurity.oauth2Login(Customizer.withDefaults());
        
      //oauth custom login
        httpSecurity.oauth2Login(oauth ->{
        	oauth.loginPage("/login");
        	oauth.successHandler(oauthHandler);
        });
        
        
        
        
        
        // implplementation of SecurityFilterChain
        DefaultSecurityFilterChain defaultSecurityFilterChain = httpSecurity.build();
        return defaultSecurityFilterChain;

    }

    // user create and login using java with in memeory service
    // @Bean
    // public UserDetailsService userDetailsService() {
    // UserDetails user1 = User.withUsername("admin")
    // .password("{noop}Saroj@789")
    // .roles("ADMIN", "USER")
    // .build();

    // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1);
    // return inMemoryUserDetailsManager;
    // }

}
