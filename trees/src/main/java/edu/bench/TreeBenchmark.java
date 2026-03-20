package edu.bench;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

import edu.dsu.DSU;
import edu.trees.AVLTree;

import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Benchmark;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 2)
@Fork(1)
public class TreeBenchmark {
    static final int N = 100_000;
    Integer[] data;
    AVLTree<Integer> avl;
    TreeMap<Integer, Integer> treeMap;
    DSU dsu;
    Random rng = new Random(42);

    @Setup(Level.Trial)
    public void setup() {
        data = new Integer[N];
        for (int i = 0; i < N; i++)
            data[i] = rng.nextInt(N * 10);
        avl = new AVLTree<>();
        treeMap = new TreeMap<>();
        Arrays.stream(data).forEach(x -> {
            avl.insert(x);
            treeMap.put(x, x);
        });
        dsu = new DSU(N);
    }

    @Benchmark
    public boolean avlContains() {
        return avl.contains(data[rng.nextInt(N)]);
    }

    @Benchmark
    public boolean treeMapContains() {
        return treeMap.containsKey(data[rng.nextInt(N)]);
    }

    @Benchmark
    public boolean dsuConnected() {
        return dsu.connected(rng.nextInt(N), rng.nextInt(N));
    }
}