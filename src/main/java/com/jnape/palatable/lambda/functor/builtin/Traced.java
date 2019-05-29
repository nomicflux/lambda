package com.jnape.palatable.lambda.functor.builtin;

import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.comonad.builtin.ComonadTraced;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.monoid.Monoid;

public final class Traced<A, M extends Monoid<A>, B> implements Comonad<B, Traced<A, M, ?>>, ComonadTraced<A, M, B, Traced<A, M, ?>> {
    private Fn1<? super A, ? extends B> trace;
    private Monoid<A> aMonoid;

    private Traced(Fn1<? super A, ? extends B> t, Monoid<A> m) {
        this.trace = t;
        this.aMonoid = m;
    }

    public static <A, M extends Monoid<A>, B> Traced<A, M, B> traced(Fn1<? super A, ? extends B> t, Monoid<A> m) {
        return new Traced<>(t, m);
    }

    public final B runTrace(A a) {
        return trace.apply(a);
    }

    @Override
    public Monoid<A> getMonoid() {
        return aMonoid;
    }

    @Override
    public B extract() {
        return trace.apply(aMonoid.identity());
    }

    @Override
    public <B1> Comonad<B1, Traced<A, M, ?>> extendImpl(Fn1<? super Comonad<B, Traced<A, M, ?>>, ? extends B1> f) {
        return traced(a -> f.apply(traced(trace.diMapL(aMonoid.apply(a)), aMonoid)), aMonoid);
    }
}
