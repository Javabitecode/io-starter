package com.zuzex.ioclientspringbootstarter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "io.client")
public class IoClientProperties {
    private String host;
    private int port;
}
