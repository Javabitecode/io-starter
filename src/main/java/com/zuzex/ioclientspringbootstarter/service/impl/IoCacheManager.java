package com.zuzex.ioclientspringbootstarter.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zuzex.ioclientspringbootstarter.service.IoGrpcService;
import com.zuzex.ioclientspringbootstarter.service.IoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class IoCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

    public static List<TypeReference<?>> ioReferenceList;

    @Autowired
    private IoGrpcService ioGrpcService;

    @Autowired
    private IoService ioService;

    public IoCacheManager(List<TypeReference<?>> referenceList) {
        IoCacheManager.ioReferenceList = referenceList;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = this.cacheMap.get(name);
        if (cache == null) {
            synchronized (this.cacheMap) {
                cache = this.cacheMap.get(name);
                if (cache == null)
                    cache = new IoCache(ioGrpcService, ioService, name);
            }
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }
}
