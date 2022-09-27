package com.zuzex.io;

import com.zuzex.io.config.DeleteState;
import com.zuzex.io.config.ReadState;
import com.zuzex.io.config.WriteState;
import lombok.SneakyThrows;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.zuzex.io.config.IoClassInitialization.ioGrpcService;


@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
public class IoBenchmarkMain {

    @SneakyThrows
    public static void main(String[] args) {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 1)
    @Warmup(iterations = 2, time = 1)
    public void loopWrite(Blackhole bh, WriteState state) {
        for (int i = 0; i < state.count; i++) {
            var test = ioGrpcService.save(Descriptor.GrpcIoData.newBuilder()
                    .setKey(state.keyList.get(i))
                    .setValue(String.valueOf(UUID.randomUUID()))
                    .build());
            bh.consume(test);
        }
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 1)
    @Warmup(iterations = 2, time = 1)
    public void loopRead(Blackhole bh, ReadState state) {
        for (int i = 0; i < state.count; i++) {
            var test = ioGrpcService.read(Descriptor.GrpcIoKey.newBuilder()
                    .setKey(state.randomKey)
                    .build());
            bh.consume(test);
        }
    }

    @Benchmark
    @Fork(value = 2)
    @Measurement(iterations = 2, time = 1)
    @Warmup(iterations = 2, time = 1)
    public void loopDelete(Blackhole bh, DeleteState state) {
        for (int i = 0; i < state.count; i++) {
            var test = ioGrpcService.delete(Descriptor.GrpcIoKey.newBuilder()
                    .setKey(state.randomKeyForDelete)
                    .build());
            bh.consume(test);
        }
    }
}
