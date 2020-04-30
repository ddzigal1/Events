package com.example.SystemEvents.Services;



import com.example.SystemEvents.Repository.ISystemEventRepository;
import com.lambda.grpc.systemevent.Greeting;
import com.lambda.grpc.systemevent.HelloWorldServiceGrpc;
import com.lambda.grpc.systemevent.Reply;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class HelloWorldServiceImplementation extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {



    @Override
    public void sayHello(Greeting request, StreamObserver<Reply> responseObserver) {


        Reply.Builder reply = Reply.newBuilder();
        reply.setMessage("Hi there");

        responseObserver.onNext(reply.build());
        responseObserver.onCompleted();
    }
}
