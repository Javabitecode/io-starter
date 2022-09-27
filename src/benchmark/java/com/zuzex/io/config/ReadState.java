package com.zuzex.io.config;

import com.zuzex.io.Descriptor;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.zuzex.io.config.IoClassInitialization.ioGrpcService;

@State(Scope.Benchmark)
public class ReadState {
    private static final int CAPACITY = 1500;
    public final List<String> keyList = Stream.generate(() -> UUID.randomUUID().toString())
            .limit(CAPACITY)
            .collect(Collectors.toList());

    @Param({"1", "10", "100", "1000"})
    public int count;
    public String randomKey;

    @Setup(Level.Trial)
    public void setUp() {
        IntStream.range(0, CAPACITY).forEach(i -> ioGrpcService.save(
                Descriptor.GrpcIoData.newBuilder()
                        .setKey(keyList.get(i))
                        .build())
        );
        randomKey = keyList.get(new Random().nextInt(keyList.size() - 1));
    }
}
