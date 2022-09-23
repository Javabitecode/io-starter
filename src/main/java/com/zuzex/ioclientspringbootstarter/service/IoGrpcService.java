package com.zuzex.ioclientspringbootstarter.service;


import com.zuzex.io.Descriptor.GrpcIoResultExecution;
import com.zuzex.ioclientspringbootstarter.config.IoClientProperties;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.zuzex.io.Descriptor.GrpcIoData;
import static com.zuzex.io.Descriptor.GrpcIoKey;
import static com.zuzex.io.IoProtoServiceGrpc.IoProtoServiceBlockingStub;
import static com.zuzex.io.IoProtoServiceGrpc.newBlockingStub;


@Slf4j
@Service
@RequiredArgsConstructor
public class IoGrpcService {

    private final IoClientProperties properties;

    public GrpcIoResultExecution save(GrpcIoData ioData) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(properties.getHost(), properties.getPort()).usePlaintext().build();
        IoProtoServiceBlockingStub blockingStub =
                newBlockingStub(channel);
        GrpcIoResultExecution ioResultExecution = blockingStub.save(ioData);
        log.info("Response from IO Service: {}", ioResultExecution.getResult());
        channel.shutdown();
        return ioResultExecution;
    }

    public GrpcIoData read(GrpcIoKey key) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(properties.getHost(), properties.getPort()).usePlaintext().build();
        IoProtoServiceBlockingStub blockingStub =
                newBlockingStub(channel);
        GrpcIoData ioData = blockingStub.read(key);
        log.info("This key: " + ioData.getKey() + " This value: " + ioData.getValue());
        channel.shutdown();
        return ioData;
    }

    public GrpcIoResultExecution delete(GrpcIoKey key) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(properties.getHost(), properties.getPort()).usePlaintext().build();
        IoProtoServiceBlockingStub blockingStub =
                newBlockingStub(channel);
        GrpcIoResultExecution resultExecution = blockingStub.delete(key);
        log.info("This result: " + resultExecution.getResultValue() + " This exception: " + resultExecution.getException());
        channel.shutdown();
        return resultExecution;
    }
}

