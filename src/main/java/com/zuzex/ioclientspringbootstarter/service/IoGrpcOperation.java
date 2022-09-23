package com.zuzex.ioclientspringbootstarter.service;

import static com.zuzex.io.Descriptor.*;

public interface IoGrpcOperation {
    GrpcIoData read(GrpcIoKey key);

    GrpcIoResultExecution save(GrpcIoData ioData);

}
