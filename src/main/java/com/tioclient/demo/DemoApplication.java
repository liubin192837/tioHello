package com.tioclient.demo;

import com.tioclient.demo.client.ClientStarter;
import com.tioclient.demo.server.ServerStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    private static ServerStart serverStart;
    @Autowired
    private static ClientStarter clientStarter;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        try {
            ServerStart serverStart = new ServerStart();
            ClientStarter clientStarter = new ClientStarter();
            serverStart.start();
            clientStarter.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
