package com.zuzex.ioclientspringbootstarter.service;

public interface IoService {

    Object read(Object key);
    void write(Object key, Object value);

    void deleteByKey(Object key);
}
