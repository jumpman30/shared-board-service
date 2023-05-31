package com.lapr4.sharedboardservice.connection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ConnectionProviderConfig {

    @Bean
    public ConnectionProvider connectionProvider() throws IOException {
        return new ConnectionProvider();
    }
}
