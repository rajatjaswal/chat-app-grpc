package com.grpc.chat;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class ChatApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(3000)
                .build();

        server.start();
        server.awaitTermination();
    }
}
