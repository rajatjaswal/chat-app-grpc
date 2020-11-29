package com.grpc.chat;

import io.grpc.Status;
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
        User from = request.getFrom();
        User to = request.getTo();
        List<Message> msg = messages
                .stream()
                .filter(x -> x.getFrom().getName().equals(from.getName()) && x.getTo().getName().equals(to.getName()))
                .collect(Collectors.toList());
        if(msg.isEmpty()){
            responseObserver.onError(Status.NOT_FOUND.withDescription("No Message Found").asRuntimeException());
        }else{
            msg.forEach(responseObserver::onNext);
            responseObserver.onCompleted();
        }
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

