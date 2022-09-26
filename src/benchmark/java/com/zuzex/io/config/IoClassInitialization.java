package com.zuzex.io.config;

import com.zuzex.io.IoProtoServiceGrpc;
import com.zuzex.ioclientspringbootstarter.config.IoClientProperties;
import com.zuzex.ioclientspringbootstarter.service.IoGrpcService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import static com.zuzex.io.IoProtoServiceGrpc.newBlockingStub;

public class IoClassInitialization {
    private static final IoClientProperties properties = new IoClientProperties();

    static {
        properties.setHost("localhost");
        properties.setPort(7071);
    }

    public static ManagedChannel getChannel() {
        return ManagedChannelBuilder
                .forAddress(properties.getHost(), properties.getPort()).usePlaintext().build();
    }

    public static IoProtoServiceGrpc.IoProtoServiceBlockingStub blockingStub() {
        return newBlockingStub(getChannel());
    }

    public static IoGrpcService ioGrpcService = new IoGrpcService(blockingStub());

}
