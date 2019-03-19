package com.jnape.palatable.lambda.comonad;

import com.jnape.palatable.lambda.functions.builtin.fn1.Id;
import com.jnape.palatable.lambda.functor.Functor;

import java.util.function.Function;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Id.id;

public interface Comonad<A, W extends Comonad> extends Functor<A, W> {
    A extract();

    <B> Comonad<B, W> extend(Function<? super Comonad<A, W>, ? extends B> f);

    @Override
    default <B> Comonad<B, W> fmap(Function<? super A, ? extends B> fn) {
        return extend(wa -> fn.apply(wa.extract()));
    }

    // `extend` is left to be implemented, and `duplicate` is given a definition based off of it, in symmetry with `Monad`
    // However, it might be better to reverse this for `Comonad`, as there may be a more natural definition for `duplicate`
    // in an implementation for the interface
    static <W extends Comonad, A, WA extends Comonad<A, W>> Comonad<? extends Comonad<A, W>, W> duplicate(Comonad<A, W> wa) {
        return wa.extend(id());
    }
}
