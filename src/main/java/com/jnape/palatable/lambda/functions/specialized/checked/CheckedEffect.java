package com.jnape.palatable.lambda.functions.specialized.checked;

import com.jnape.palatable.lambda.adt.Unit;
import com.jnape.palatable.lambda.functions.Effect;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.io.IO;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.jnape.palatable.lambda.functions.specialized.checked.Runtime.throwChecked;
import static com.jnape.palatable.lambda.io.IO.io;

/**
 * Specialized {@link Effect} that can throw any {@link Throwable}.
 *
 * @param <T> The {@link Throwable} type
 * @param <A> The input type
 * @see CheckedRunnable
 * @see CheckedFn1
 * @see Effect
 */
@FunctionalInterface
public interface CheckedEffect<T extends Throwable, A> extends Effect<A>, CheckedFn1<T, A, IO<Unit>> {

    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(A a) {
        try {
            checkedAccept(a);
        } catch (Throwable t) {
            throw throwChecked(t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default IO<Unit> apply(A a) {
        return io(() -> accept(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default IO<Unit> checkedApply(A a) throws T {
        return apply(a);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default <Z> CheckedEffect<T, Z> diMapL(Function<? super Z, ? extends A> fn) {
        return Effect.super.diMapL(fn)::checkedAccept;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default <Z> CheckedEffect<T, Z> contraMap(Function<? super Z, ? extends A> fn) {
        return Effect.super.contraMap(fn)::checkedAccept;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default <Z> CheckedEffect<T, Z> compose(Function<? super Z, ? extends A> before) {
        return Effect.super.compose(before)::checkedAccept;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default <C> CheckedEffect<T, A> discardR(Applicative<C, Fn1<A, ?>> appB) {
        return Effect.super.discardR(appB)::checkedAccept;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default CheckedEffect<T, A> andThen(Consumer<? super A> after) {
        return Effect.super.andThen(after)::checkedAccept;
    }

    /**
     * Convenience static factory method for constructing a {@link CheckedEffect} without an explicit cast or type
     * attribution at the call site.
     *
     * @param checkedEffect the checked effect
     * @param <T>           the inferred Throwable type
     * @param <A>           the input type
     * @return the checked effect
     */
    static <T extends Throwable, A> CheckedEffect<T, A> checked(CheckedEffect<T, A> checkedEffect) {
        return checkedEffect;
    }
}
