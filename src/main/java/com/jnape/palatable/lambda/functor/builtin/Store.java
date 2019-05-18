package com.jnape.palatable.lambda.functor.builtin;

import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.functions.Fn1;

public final class Store<S, A> implements Comonad<A, Store<S, ?>> {
    private Fn1<? super S, ? extends A> peek;
    private S cursor;

    private Store(Fn1<? super S, ? extends A> f, S s) {
        this.peek = f;
        this.cursor = s;
    }

    public static <S, A> Store<S, A> store(Fn1<? super S, ? extends A> f, S s)  {
        return new Store<>(f, s);
    }

    @Override
    public A extract() {
        return peek.apply(cursor);
    }

    @Override
    public <B> Comonad<B, Store<S, ?>> extend(Fn1<? super Comonad<A, Store<S, ?>>, ? extends B> f) {
        return store(s -> f.apply(store(peek, s)), cursor);
    }
}
