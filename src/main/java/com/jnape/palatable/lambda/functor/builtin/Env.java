package com.jnape.palatable.lambda.functor.builtin;

import com.jnape.palatable.lambda.adt.product.Product2;
import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.comonad.builtin.ComonadEnv;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.builtin.fn1.Id;
import com.jnape.palatable.lambda.functor.Functor;

import java.util.Objects;

import static com.jnape.palatable.lambda.adt.hlist.HList.tuple;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Id.id;

public final class Env<E, A> implements Comonad<A, Env<E, ?>>, ComonadEnv<E, A, Env<E, ?>>, Product2<E, A> {
    private E env;
    private A value;

    private Env(E e, A a) {
        this.env = e;
        this.value = a;
    }

    public static <E, Z> Env<E, Z> env(E env, Z value) {
        return new Env<>(env, value);
    }

    public final E ask() {
        return this.env;
    };

    public final <R> R asks(Fn1<? super E, ? extends R> f) {
        return f.apply(this.ask());
    }

    @Override
    public A extract() {
        return this.value;
    }

    @Override
    public <B> Comonad<B, Env<E, ?>> extendImpl(Fn1<? super Comonad<A, Env<E, ?>>, ? extends B> f) {
        return env(env, f.apply(this));
    }

    @Override
    public E _1() {
        return env;
    }

    @Override
    public A _2() {
        return value;
    }

    @Override
    public String toString() {
        return env.toString() + ": " + value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Env && Objects.equals(value, ((Env) other).value) && Objects.equals(env, ((Env) other).env);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tuple(env, value));
    }

    @Override
    public <B> Env<E, B> fmap(Fn1<? super A, ? extends B> fn) {
        return env(env, fn.apply(value));
    }
}
