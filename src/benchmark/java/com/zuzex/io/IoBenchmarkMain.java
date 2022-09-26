package com.zuzex.io;

import com.zuzex.io.config.BenchmarkState;
import lombok.SneakyThrows;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.zuzex.io.config.IoClassInitialization.ioGrpcService;


@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class IoBenchmarkMain {


    @SneakyThrows
    public static void main(String[] args) {
        org.openjdk.jmh.Main.main(args);
     /*   Options opt = new OptionsBuilder()
                .include(IoBenchmarkMain.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();*/
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 1)
    @Warmup(iterations = 2, time = 1)
    public void loopWrite(Blackhole bh, BenchmarkState state) {
        for (int i = 0; i < state.count; i++) {
            var test = ioGrpcService.save(Descriptor.GrpcIoData.newBuilder()
                    .setKey(state.list.get(i))
                    .setValue(String.valueOf(UUID.randomUUID()))
                    .build());
            bh.consume(test);
        }
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 1)
    @Warmup(iterations = 2, time = 1)
    public void loopRead(Blackhole bh, BenchmarkState state) {
        for (int i = 0; i < state.count; i++) {
            var test = ioGrpcService.read(Descriptor.GrpcIoKey.newBuilder()
                    .setKey(state.randomKeyForReadAndWrite)
                    .build());
            bh.consume(test);
        }
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 1)
    @Warmup(iterations = 2, time = 1)
    public void loopDelete(Blackhole bh, BenchmarkState state) {
        for (int i = 0; i < state.count; i++) {
            var test = ioGrpcService.delete(Descriptor.GrpcIoKey.newBuilder()
                    .setKey(state.randomKeyForDelete)
                    .build());
            bh.consume(test);
        }
    }
}
