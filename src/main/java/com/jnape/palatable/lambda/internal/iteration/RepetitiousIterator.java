package com.jnape.palatable.lambda.internal.iteration;

public final class RepetitiousIterator<A> extends InfiniteIterator<A> {

    private final A value;

    public RepetitiousIterator(A value) {
        this.value = value;
    }

    @Override
    public A next() {
        return value;
    }
}
