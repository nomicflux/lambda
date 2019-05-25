package com.jnape.palatable.lambda.functor.builtin;

import com.jnape.palatable.lambda.adt.product.Product2;
import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.functions.Fn1;

public final class Env<E, A> implements Comonad<A, Env<E, ?>>, Product2<E, A> {
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
    public <B> Env<E, B> extendImpl(Fn1<? super Comonad<A, Env<E, ?>>, ? extends B> f) {
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

    public static void main(String[] args) {
        Env<String, Integer> hello = env("hello", 1);
        System.out.println(hello.ask());
        System.out.println(hello.extract());
        System.out.println(hello.extend(e -> 1 + e.extract()));
        System.out.println(hello.extend((Env<String, Integer> e) -> 1 + e.ask().length()));
    }
}
