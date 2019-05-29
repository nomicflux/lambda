package com.jnape.palatable.lambda.functor.builtin;

import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.comonad.builtin.ComonadStore;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.Functor;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;

public final class Store<S, A> implements Comonad<A, Store<S, ?>>, ComonadStore<S, A, Store<S, ?>> {
    private Fn1<? super S, ? extends A> storage;
    private S cursor;

    private Store(Fn1<? super S, ? extends A> f, S s) {
        this.storage = f;
        this.cursor = s;
    }

    public static <S, A> Store<S, A> store(Fn1<? super S, ? extends A> f, S s) {
        return new Store<>(f, s);
    }

    @Override
    public final A peeks(Fn1<? super S, ? extends S> f) {
        return storage.contraMap(f).apply(cursor);
    }

    @Override
    public final Store<S, A> seeks(Fn1<? super S, ? extends S> f) {
        return store(storage, f.apply(cursor));
    }

    //public <F extends Functor<?, ? extends F>, FA extends Functor<A, ? extends F>, FS extends Functor<S, ? extends S>> FA experiment(Fn1<? super S, ? extends FS> f) {
    //public <F extends Functor<?, ? extends F>, FA extends Functor<A, F>, FS extends Functor<S, F>> FA experiment(Fn1<? super S, ? extends FS> f) {
    public <F extends Functor<?, F>> Functor<A, F> experiment(Fn1<? super S, ? extends Functor<S, F>> f) {
        return f.apply(cursor).fmap(c -> peeks(constantly(c)));
    }

    @Override
    public <B> Comonad<B, Store<S, ?>> extendImpl(Fn1<? super Comonad<A, Store<S, ?>>, ? extends B> f) {
        return store(s -> f.apply(store(storage, s)), cursor);
    }
}
