package com.zuzex.ioclientspringbootstarter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuzex.ioclientspringbootstarter.service.IoGrpcService;
import com.zuzex.ioclientspringbootstarter.service.IoSerializer;
import com.zuzex.ioclientspringbootstarter.service.IoService;
import com.zuzex.ioclientspringbootstarter.service.impl.IoSimpleSerializer;
import com.zuzex.ioclientspringbootstarter.service.impl.IoSimpleService;
import com.zuzex.ioclientspringbootstarter.service.impl.MyCustomKeyGenerator;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.zuzex.io.IoProtoServiceGrpc.IoProtoServiceBlockingStub;
import static com.zuzex.io.IoProtoServiceGrpc.newBlockingStub;

@Configuration
@ConditionalOnProperty(prefix = "io.client", name = {"host", "port"})
@EnableConfigurationProperties(IoClientProperties.class)
public class IoClientAutoConfiguration extends CachingConfigurerSupport {

    @Autowired
    private IoClientProperties ioProperties;

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new MyCustomKeyGenerator();
    }

    @Bean
    ManagedChannel getChannel() {
        return ManagedChannelBuilder
                .forAddress(ioProperties.getHost(), ioProperties.getPort()).usePlaintext().build();
    }

    @Bean
    IoProtoServiceBlockingStub blockingStub() {
        return newBlockingStub(getChannel());
    }

    @Bean
    @ConditionalOnMissingBean
    public IoGrpcService ioGrpcService() {
        System.out.println(ioProperties.getHost());
        System.out.println(ioProperties.getPort());
        return new IoGrpcService(blockingStub());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public IoSerializer ioSerializer() {
        return new IoSimpleSerializer(objectMapper());
    }

    @Bean
    @ConditionalOnMissingBean
    public IoService ioService() {
        return new IoSimpleService(ioGrpcService(), ioSerializer());
    }
}
