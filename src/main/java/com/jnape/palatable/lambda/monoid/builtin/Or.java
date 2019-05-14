package com.jnape.palatable.lambda.monoid.builtin;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.specialized.BiPredicate;
import com.jnape.palatable.lambda.monoid.Monoid;

import java.util.function.Function;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;
import static com.jnape.palatable.lambda.functions.builtin.fn2.Find.find;

/**
 * A {@link Monoid} instance formed by <code>Boolean</code>. Equivalent to logical <code>||</code>.
 *
 * @see And
 * @see Monoid
 */
public final class Or implements Monoid<Boolean>, BiPredicate<Boolean, Boolean> {

    private static final Or INSTANCE = new Or();

    private Or() {
    }

    @Override
    public Boolean identity() {
        return false;
    }

    @Override
    public Boolean checkedApply(Boolean x, Boolean y) {
        return x || y;
    }

    @Override
    public boolean test(Boolean x, Boolean y) {
        return apply(x, y);
    }

    @Override
    public <B> Boolean foldMap(Function<? super B, ? extends Boolean> fn, Iterable<B> bs) {
        return find(fn, bs).fmap(constantly(true)).orElse(false);
    }

    @Override
    public Or flip() {
        return this;
    }

    public static Or or() {
        return INSTANCE;
    }

    public static Fn1<Boolean, Boolean> or(Boolean x) {
        return or().apply(x);
    }

    public static Boolean or(Boolean x, Boolean y) {
        return or(x).apply(y);
    }
}
