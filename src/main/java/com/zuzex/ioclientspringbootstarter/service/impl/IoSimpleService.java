package com.zuzex.ioclientspringbootstarter.service.impl;

import com.zuzex.ioclientspringbootstarter.service.IoGrpcService;
import com.zuzex.ioclientspringbootstarter.service.IoSerializer;
import com.zuzex.ioclientspringbootstarter.service.IoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.zuzex.io.Descriptor.*;

@Slf4j
@RequiredArgsConstructor
public class IoSimpleService implements IoService {

    private final IoGrpcService grpcService;
    private final IoSerializer serializer;


    public Object read(Object key) {
        GrpcIoData test = grpcService.read(GrpcIoKey.newBuilder()
                .setKey(String.valueOf(key))
                .build());
        return serializer.deserialization(test.getValue());
    }

    public void write(Object key, Object value) {
        String valueGrpc = serializer.writeValueAsString(value);
        GrpcIoResultExecution ioResultExecution = grpcService.save(GrpcIoData.newBuilder()
                .setKey(String.valueOf(key))
                .setValue(valueGrpc)
                .build());
    }

    @Override
    public void deleteByKey(Object key) {
        GrpcIoResultExecution ioResultExecution = grpcService.delete(GrpcIoKey.newBuilder()
                .setKey(String.valueOf(key))
                .build());
    }
}
