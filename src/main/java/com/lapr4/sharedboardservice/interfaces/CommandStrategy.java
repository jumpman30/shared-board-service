package com.lapr4.sharedboardservice.interfaces;

import java.io.IOException;
import java.net.Socket;

public interface CommandStrategy {

    void apply();

    void reply(Socket sharedBoardChannel) throws IOException;
}
