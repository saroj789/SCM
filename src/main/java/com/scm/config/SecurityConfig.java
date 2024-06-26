package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    // user create and login using java with in memeory service
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("admin")
                .password("{noop}Saroj@789")
                .roles("ADMIN", "USER")
                .build();

        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1);
        return inMemoryUserDetailsManager;
    }

}
