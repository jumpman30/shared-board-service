package com.lapr4.sharedboardservice.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.ServerSocket;
import java.io.IOException;


public class ConnectionProvider {
    private final ServerSocket serverSocket;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionProvider.class);

    public ConnectionProvider() throws IOException {
        //todo: should we improve the logic to open more ports?
        this.serverSocket = new ServerSocket(7077);
        logger.info("Opened port 7077 for connection");
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }
}
