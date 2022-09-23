package com.zuzex.ioclientspringbootstarter.service.impl;

import com.zuzex.ioclientspringbootstarter.service.IoGrpcService;
import com.zuzex.ioclientspringbootstarter.service.IoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;


@Slf4j
@AllArgsConstructor
public class IoCache implements Cache {

    private IoGrpcService ioGrpcService;
    private IoService ioService;
    private final String cacheName;

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public Object getNativeCache() {
        return ioGrpcService;
    }

    @Override
    public ValueWrapper get(Object key) {
        try {
            var result = ioService.read(key);
            log.debug("Object from deserialization: {}", result);
            return () -> result;
        } catch (Exception ex) {
            log.info("Value not found in storage");
            return null;
        }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        ioService.write(key, value);
    }

    @Override
    public void evict(Object key) {
        ioService.deleteByKey(key);
    }

    @Override
    public void clear() {
        //CLEAR ALL CACHE
    }
}
