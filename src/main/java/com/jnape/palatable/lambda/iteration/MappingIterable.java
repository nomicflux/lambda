package com.jnape.palatable.lambda.iteration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static com.jnape.palatable.lambda.functions.builtin.fn3.FoldLeft.foldLeft;
import static java.util.Collections.singletonList;

public final class MappingIterable<A, B> implements Iterable<B> {
    private final Iterable<A>          as;
    private final List<Function<?, ?>> mappers;

    @SuppressWarnings("unchecked")
    public MappingIterable(Function<? super A, ? extends B> fn, Iterable<A> as) {
        List<Function<?, ?>> mappers = new ArrayList<>(singletonList(fn));
        while (as instanceof MappingIterable<?, ?>) {
            MappingIterable<?, ?> nested = (MappingIterable<?, ?>) as;
            as = (Iterable<A>) nested.as;
            mappers.addAll(0, nested.mappers);
        }
        this.as = as;
        this.mappers = mappers;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<B> iterator() {
        Function<Object, Object> fnComposedOnTheHeap = a -> foldLeft((x, fn) -> ((Function<Object, Object>) fn).apply(x),
                                                                     a, mappers);
        return new MappingIterator<>((Function<? super A, ? extends B>) fnComposedOnTheHeap, as.iterator());
    }
}
