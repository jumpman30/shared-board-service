package com.lapr4.sharedboardservice;
import com.lapr4.sharedboardservice.connection.ConnectionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//README file contains instructions to run this app separately

//todo: consistent way to deal with socket and server closing
//todo: kubernets connection
@SpringBootApplication
public class SharedBoardServiceApplication implements CommandLineRunner {
	@Autowired
	private ConnectionHandler connectionHandler;

	public static void main(String[] args) {
		SpringApplication.run(SharedBoardServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		connectionHandler.handleConnectionRequest();
	}
}
