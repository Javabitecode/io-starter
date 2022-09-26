package com.zuzex.io.config;

import com.zuzex.io.Descriptor;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.zuzex.io.config.IoClassInitialization.ioGrpcService;

@State(Scope.Benchmark)
public class BenchmarkState {

    @Param({"1", "10", "100", "1000"})
    public int count;

    private static final int CAPACITY = 3000;

    public List<String> list = new ArrayList<>();


    {
        for (int i = 0; i < CAPACITY; i++) {
            list.add(String.valueOf(UUID.randomUUID()));
        }
    }

    public String randomKeyForReadAndWrite;

    public String randomKeyForDelete;

    @Setup(Level.Trial)
    public void setUp() {
        for (int i = 0; i < CAPACITY; i++) {
            ioGrpcService.save(Descriptor.GrpcIoData.newBuilder()
                    .setKey(list.get(i)).build());
        }
        for (int i = CAPACITY - 1; i >= CAPACITY / 2; i--) {
            ioGrpcService.save(Descriptor.GrpcIoData.newBuilder()
                    .setKey(list.get(i)).build());
        }
        randomKeyForReadAndWrite = list.get(new Random().nextInt((list.size() - 1) / 2));
        randomKeyForDelete = list.get(new Random()
                .nextInt(list.size() - (list.size() - 1) / 2) + (list.size() - 1) / 2);
    }
}
