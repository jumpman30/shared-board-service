package com.lapr4.sharedboardservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lapr4.sharedboardservice.dtos.Message;
import com.lapr4.sharedboardservice.enums.Code;
import com.lapr4.sharedboardservice.interfaces.CommandStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
@Service
public class TestConnectionService implements CommandStrategy {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void apply() {
        logger.info("Connection Up");
    }

    @Override
    public void reply(Socket sharedBoardChannel) throws IOException {
        Message replyMessage = new Message(Code.ACK);
        String responseJson = objectMapper.writeValueAsString(replyMessage);
        OutputStream outputStream = sharedBoardChannel.getOutputStream();
        outputStream.write(responseJson.getBytes());
        outputStream.flush();
    }
}
