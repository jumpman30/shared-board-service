package com.lapr4.sharedboardservice.connection;
import com.lapr4.sharedboardservice.consumers.SharedBoardConsumer;
import com.lapr4.sharedboardservice.repository.UserSessionRepository;
import com.lapr4.sharedboardservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class ConnectionHandler {

    private final int POOL_SIZE = 10;
    private final ServerSocket sharedBoardSocket;
    private final UserSessionRepository userSessionRepository;

   private final BlockingQueue<SharedBoardConsumer> consumerPool = new LinkedBlockingQueue<>();

    private final ExecutorService threadPool;

    @Autowired
    public ConnectionHandler(ConnectionProvider connectionProvider, UserSessionRepository userSessionRepository) {
        this.sharedBoardSocket = connectionProvider.getServerSocket();
        this.userSessionRepository = userSessionRepository;
        this.threadPool = Executors.newFixedThreadPool(POOL_SIZE);
        this.prePopulateConsumerPool();
    }

    public void handleConnectionRequest() throws IOException {
        try {
            while (true) {
                Socket socket = sharedBoardSocket.accept();
                this.openChannel(socket);
            }
        } catch (Exception e){
            sharedBoardSocket.close();
            throw new Error("Connection failed with error " + e.getMessage());
        }
    }

    public void openChannel(Socket connectionSocket) throws InterruptedException {
        SharedBoardConsumer consumer = consumerPool.take();
        consumer.openChannel(connectionSocket);
        threadPool.submit(consumer);
    }

    private void prePopulateConsumerPool(){
        for (int i = 0; i < POOL_SIZE; i++) {
            AuthService authService = new AuthService();
            SharedBoardConsumer consumer = new SharedBoardConsumer(authService, this.userSessionRepository);;
            consumerPool.add(consumer);
        }
    }
}
