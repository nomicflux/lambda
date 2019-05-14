package com.jnape.palatable.lambda.functions.builtin.fn1;

import com.jnape.palatable.lambda.functions.specialized.BiPredicate;
import com.jnape.palatable.lambda.functions.specialized.Predicate;

import java.util.function.Function;

/**
 * Negate a predicate function.
 *
 * @param <A> the input argument type
 */
public final class Not<A> implements BiPredicate<Function<? super A, ? extends Boolean>, A> {
    private static final Not<?> INSTANCE = new Not<>();

    private Not() {
    }

    @Override
    public Boolean checkedApply(Function<? super A, ? extends Boolean> pred, A a) {
        return !pred.apply(a);
    }

    @SuppressWarnings("unchecked")
    public static <A> Not<A> not() {
        return (Not<A>) INSTANCE;
    }

    public static <A> Predicate<A> not(Function<? super A, ? extends Boolean> pred) {
        return Not.<A>not().apply(pred);
    }

    public static <A> Boolean not(Function<? super A, ? extends Boolean> pred, A a) {
        return not(pred).apply(a);
    }
}
