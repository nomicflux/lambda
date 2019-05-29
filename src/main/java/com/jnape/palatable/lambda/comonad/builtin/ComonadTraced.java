package com.jnape.palatable.lambda.comonad.builtin;

import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.monoid.Monoid;

public interface ComonadTraced<A, M extends Monoid<A>, B, W extends Comonad<?, W>> extends Comonad<B, W> {
    B runTrace(A a);

    Monoid<A> getMonoid();

    @Override
    default B extract() {
        return runTrace(getMonoid().identity());
    }
}
