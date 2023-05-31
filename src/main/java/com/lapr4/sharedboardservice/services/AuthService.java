package com.lapr4.sharedboardservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lapr4.sharedboardservice.dtos.Message;
import com.lapr4.sharedboardservice.enums.Code;
import com.lapr4.sharedboardservice.repository.UserSessionRepository;
import com.lapr4.sharedboardservice.session.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

@Service
public class AuthService {

    private final String hashSource = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "0123456789";

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void authenticate(UserSessionRepository sessionRepository, Socket sharedBoardChannel) {
        //todo: For testing purposes on this sprint we by pass authentication
        String hash = this.generateSessionHash();
        UserSession session = new UserSession();
        session.authenticate();
        sessionRepository.repository.put(hash, session);
        logger.info("User authenticated...");
        this.reply(sharedBoardChannel, hash);
    }

    private void reply(Socket sharedBoardChannel, String hashSession) {
        try {
            Message replyMessage = new Message(Code.ACK, hashSession);
            String responseJson = objectMapper.writeValueAsString(replyMessage);
            OutputStream outputStream = sharedBoardChannel.getOutputStream();
            outputStream.write(responseJson.getBytes());
            outputStream.flush();
        }catch (Exception e){
            logger.info("Error sending reply...");
        }
    }

    private String generateSessionHash(){
        Random random = new Random();

        StringBuilder token = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            token.append(hashSource.charAt(random.nextInt(hashSource.length())));
        }
        return token.toString();
    }

}
