package com.grpc.chat;

import io.grpc.stub.StreamObserver;

import java.util.*;
import java.util.stream.Collectors;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

    List<Message> messages = new ArrayList<>();
    List<User> users = new ArrayList<>();

    // A -> B -> ["Hello", "Whatsup"]
    // m1, Hello, A, B, t1,
    // m2, Whatsup, B, A, t2,

    @Override
    public void loadMessages(LoadMessageRequest request, StreamObserver<Message> responseObserver) {
        User user = request.getFrom();
        List<Message> msg = messages
                .stream()
                .filter(x -> x.getFrom().equals(request.getFrom()) && x.getTo().equals(request.getTo()))
                .collect(Collectors.toList());
        msg.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void sendMessage(Message request, StreamObserver<MessageResponse> responseObserver) {
        messages.add(request);
        responseObserver.onNext(MessageResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Message> notify(StreamObserver<Message> responseObserver) {
        return super.notify(responseObserver);
    }
}

