package com.lapr4.sharedboardservice.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lapr4.sharedboardservice.dtos.Message;
import com.lapr4.sharedboardservice.enums.Code;
import com.lapr4.sharedboardservice.repository.UserSessionRepository;
import com.lapr4.sharedboardservice.services.AuthService;
import com.lapr4.sharedboardservice.session.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

//TODO: implement locks in database operations functions to avoid concurrency issues
@Service
public class SharedBoardConsumer implements Runnable {

    private final AuthService authService;
    private final UserSessionRepository userSessionRepository;
    private Socket sharedBoardChannel;
    private static final Logger logger = LoggerFactory.getLogger(SharedBoardConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SharedBoardConsumer(AuthService authService, UserSessionRepository sessionRepository) {
        this.authService = authService;
        this.userSessionRepository = sessionRepository;
    }


    @Override
    public void run() {
        try {

                Message message = this.decodePayload();

                if(message.code == Code.AUTH) {
                    authService.authenticate(userSessionRepository, sharedBoardChannel);
                }

                else this.validateUser(message);

        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    private Message decodePayload() throws IOException {
        DataInputStream inputStream = new DataInputStream(sharedBoardChannel.getInputStream());
        String jsonString = inputStream.readUTF();
        return objectMapper.readValue(jsonString, Message.class);
    }

    public void openChannel(Socket connectionSocket){
        this.sharedBoardChannel = connectionSocket;
    }

    private boolean validateUser(Message message ){

        String sessionId = message.sessionId;

        if(sessionId == null){
            String error = "Request was made without sessionId...";
            logger.info(error);
            this.replyWithError(error);
            return false;
        }

        UserSession userSession = userSessionRepository.repository.get(message.sessionId);
        if(userSession == null || !userSession.isAuthenticated()){
            String error = "User is not authenticated or does not have a session...";
            logger.info(error);
            this.replyWithError(error);
            return false;
        }

        return true;
    }

    private void replyWithError(String errorMessage){
        try {
            Message replyMessage = new Message(Code.ERR);
            replyMessage.setErrorMessage(errorMessage);
            String responseJson = objectMapper.writeValueAsString(replyMessage);
            OutputStream outputStream = sharedBoardChannel.getOutputStream();
            outputStream.write(responseJson.getBytes());
            outputStream.flush();
        }catch (Exception e){
            logger.info("Error sending reply...");
        }
    }
}
