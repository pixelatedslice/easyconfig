package com.pixelatedslice.easyconfig.impl.utils;

import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.concurrent.LinkedTransferQueue;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public class DistinctByGatherer<V, K> implements Gatherer<V, LinkedTransferQueue<V>, V> {

    private final Function<V, K> by;

    public DistinctByGatherer(@NonNull Function<V, K> by) {
        this.by = Objects.requireNonNull(by);
    }

    @Override
    public Supplier<LinkedTransferQueue<V>> initializer() {
        return LinkedTransferQueue::new;
    }

    @Override
    public Integrator<LinkedTransferQueue<V>, V, V> integrator() {
        return (vs, v, downstream) -> {
            var incomingBy = by.apply(v);
            var containsAny = vs.stream().map(by).anyMatch(value -> value.equals(incomingBy));
            if (!containsAny) {
                vs.add(v);
                downstream.push(v);
            }
            return true;
        };
    }

    @Override
    public BinaryOperator<LinkedTransferQueue<V>> combiner() {
        return (vs, vs2) -> {
            vs.addAll(vs2);
            //MAY DUPE -> NEED TO TEST
            return vs;
        };
    }
}
