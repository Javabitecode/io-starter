package com.zuzex.io;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class IoBenchmarkMain {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Param({"1000000", "10000000", "100000000"})
        public int listSize;

        public List<Integer> testList;

        @Setup(Level.Trial)
        public void setUp() {
            testList = new Random()
                    .ints()
                    .limit(listSize)
                    .boxed()
                    .collect(Collectors.toList());
        }
    }

    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void longStreamSum(Blackhole blackHole, BenchmarkState state) {
        long sum = state.testList.parallelStream().mapToLong(s -> s).sum();
        blackHole.consume(sum);
    }

}
