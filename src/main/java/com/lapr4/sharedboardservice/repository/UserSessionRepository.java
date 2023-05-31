package com.lapr4.sharedboardservice.repository;

import com.lapr4.sharedboardservice.session.UserSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
public class UserSessionRepository {

    public ConcurrentMap<String, UserSession> repository;

    public UserSessionRepository() {
        this.repository = new ConcurrentHashMap<>();
    }
}
