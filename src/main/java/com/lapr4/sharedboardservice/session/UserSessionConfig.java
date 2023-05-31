package com.lapr4.sharedboardservice.session;

import com.lapr4.sharedboardservice.repository.UserSessionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserSessionConfig {

    @Bean
    public UserSessionRepository userSession(){
        return new UserSessionRepository();

    }
}
