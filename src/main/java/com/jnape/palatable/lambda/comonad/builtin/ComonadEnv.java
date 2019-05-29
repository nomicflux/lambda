package com.jnape.palatable.lambda.comonad.builtin;

import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.functions.Fn1;

public interface ComonadEnv<E, A, W extends Comonad<?, W>> extends Comonad<A, W> {
    E ask();

    default <R> R asks(Fn1<? super E, ? extends R> f) {
        return f.apply(this.ask());
    }
}
