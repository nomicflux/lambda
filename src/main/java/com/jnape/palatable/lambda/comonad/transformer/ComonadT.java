package com.jnape.palatable.lambda.comonad.transformer;

import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.functions.Fn1;

/**
 * An interface representing a {@link Comonad} transformer.
 *
 * @param <F> the outer {@link Comonad monad}
 * @param <G> the inner {@link Comonad monad}
 * @param <A> the carrier type
 */
public interface ComonadT<F extends Comonad<?, F>, G extends Comonad<?, G>, A> extends Comonad<A, ComonadT<F, G, ?>> {

    /**
     * Extract out the composed monad out of this transformer.
     *
     * @return the composed monad
     */
    Comonad<Comonad<A, G>, F> run();

    Comonad<A, G> lower();

    /**
     * {@inheritDoc}
     */
    @Override
    A extract();

    /**
     * {@inheritDoc}
     */
    @Override
    <B> Comonad<B, ComonadT<F, G, ?>> extend(Fn1<? super Comonad<A, ComonadT<F, G, ?>>, ? extends B> f);
}
