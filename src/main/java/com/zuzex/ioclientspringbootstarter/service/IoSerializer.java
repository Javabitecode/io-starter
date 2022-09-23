package com.zuzex.ioclientspringbootstarter.service;

public interface IoSerializer {
    Object deserialization(String obj);

    String writeValueAsString(Object value);
}
