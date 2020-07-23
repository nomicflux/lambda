package com.jnape.palatable.lambda.semigroup.builtin;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.specialized.SemigroupFactory;
import com.jnape.palatable.lambda.semigroup.Semigroup;

import static com.jnape.palatable.lambda.functions.builtin.fn3.LTBy.ltBy;

/**
 * Given a mapping function from some type <code>A</code> to some {@link Comparable} type <code>B</code>, produce a
 * {@link Semigroup} over <code>A</code> that chooses between two values <code>x</code> and <code>y</code> via the
 * following rules:
 * <ul>
 * <li>If <code>x</code> is strictly less than <code>y</code> in terms of <code>B</code>, return <code>y</code></li>
 * <li>Otherwise, return <code>x</code></li>
 * </ul>
 *
 * @param <A> the value type
 * @param <B> the mapped comparison type
 * @see Max
 * @see MaxWith
 * @see MinBy
 */
public final class MaxBy<A, B extends Comparable<B>> implements SemigroupFactory<Fn1<? super A, ? extends B>, A> {

    private static final MaxBy<?, ?> INSTANCE = new MaxBy<>();

    private MaxBy() {
    }

    @Override
    public Semigroup<A> checkedApply(Fn1<? super A, ? extends B> compareFn) {
        return (x, y) -> ltBy(compareFn, y, x) ? y : x;
    }

    @SuppressWarnings("unchecked")
    public static <A, B extends Comparable<B>> MaxBy<A, B> maxBy() {
        return (MaxBy<A, B>) INSTANCE;
    }

    public static <A, B extends Comparable<B>> Semigroup<A> maxBy(Fn1<? super A, ? extends B> compareFn) {
        return MaxBy.<A, B>maxBy().apply(compareFn);
    }

    public static <A, B extends Comparable<B>> Fn1<A, A> maxBy(Fn1<? super A, ? extends B> compareFn, A x) {
        return MaxBy.<A, B>maxBy(compareFn).apply(x);
    }

    public static <A, B extends Comparable<B>> A maxBy(Fn1<? super A, ? extends B> compareFn, A x, A y) {
        return maxBy(compareFn, x).apply(y);
    }
}
