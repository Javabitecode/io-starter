package com.zuzex.io.config;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@State(Scope.Benchmark)
public class WriteState {

    private static final int CAPACITY = 1500;
    public final List<String> keyList = Stream.generate(() -> UUID.randomUUID().toString())
            .limit(CAPACITY)
            .collect(Collectors.toList());

    @Param({"1", "10", "100", "1000"})
    public int count;
}