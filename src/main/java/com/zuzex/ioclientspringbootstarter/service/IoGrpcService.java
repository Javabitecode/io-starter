package com.zuzex.ioclientspringbootstarter.service;


import com.zuzex.io.Descriptor.GrpcIoResultExecution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.zuzex.io.Descriptor.GrpcIoData;
import static com.zuzex.io.Descriptor.GrpcIoKey;
import static com.zuzex.io.IoProtoServiceGrpc.IoProtoServiceBlockingStub;


@Slf4j
@Service
@RequiredArgsConstructor
public class IoGrpcService {

    private final IoProtoServiceBlockingStub blockingStub;

    public GrpcIoResultExecution save(GrpcIoData ioData) {
        GrpcIoResultExecution ioResultExecution = blockingStub.save(ioData);
        log.info("Response from IO Service: {}", ioResultExecution.getResult());
        return ioResultExecution;
    }

    public GrpcIoData read(GrpcIoKey key) {
        GrpcIoData ioData = blockingStub.read(key);
        log.info("This key: " + ioData.getKey() + " This value: " + ioData.getValue());
        return ioData;
    }

    public GrpcIoResultExecution delete(GrpcIoKey key) {
        GrpcIoResultExecution resultExecution = blockingStub.delete(key);
        log.info("This result: " + resultExecution.getResultValue() + " This exception: " + resultExecution.getException());
        return resultExecution;
    }
}

