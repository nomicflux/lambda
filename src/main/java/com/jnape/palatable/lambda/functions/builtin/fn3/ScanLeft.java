package com.jnape.palatable.lambda.functions.builtin.fn3;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functions.Fn3;
import com.jnape.palatable.lambda.iteration.ScanningIterator;

/**
 * Given an <code>Iterable</code> of <code>A</code>s, a starting value <code>B</code>, and a
 * <code>{@link Fn2}&lt;B, A, B&gt;</code>, iteratively accumulate over the <code>Iterable</code>, collecting each
 * function application result, finally returning an <code>Iterable</code> of all the results. Note that, as the name
 * implies, this function accumulates from left to right, such that <code>scanLeft(f, 0, asList(1,2,3,4,5))</code> is
 * evaluated as <code>0, f(0, 1), f(f(0, 1), 2), f(f(f(0, 1), 2), 3), f(f(f(f(0, 1), 2), 3), 4), f(f(f(f(f(0, 1), 2),
 * 3), 4), 5)</code>.
 *
 * @param <A> The Iterable element type
 * @param <B> The accumulation type
 * @see FoldLeft
 */
public final class ScanLeft<A, B> implements Fn3<Fn2<? super B, ? super A, ? extends B>, B, Iterable<A>, Iterable<B>> {

    private static final ScanLeft<?, ?> INSTANCE = new ScanLeft<>();

    private ScanLeft() {
    }

    @Override
    public Iterable<B> checkedApply(Fn2<? super B, ? super A, ? extends B> fn, B b, Iterable<A> as) {
        return () -> new ScanningIterator<>(fn, b, as.iterator());
    }

    @SuppressWarnings("unchecked")
    public static <A, B> ScanLeft<A, B> scanLeft() {
        return (ScanLeft<A, B>) INSTANCE;
    }

    public static <A, B> Fn2<B, Iterable<A>, Iterable<B>> scanLeft(Fn2<? super B, ? super A, ? extends B> fn) {
        return ScanLeft.<A, B>scanLeft().apply(fn);
    }

    public static <A, B> Fn1<Iterable<A>, Iterable<B>> scanLeft(Fn2<? super B, ? super A, ? extends B> fn, B b) {
        return ScanLeft.<A, B>scanLeft(fn).apply(b);
    }

    public static <A, B> Iterable<B> scanLeft(Fn2<? super B, ? super A, ? extends B> fn, B b, Iterable<A> as) {
        return ScanLeft.<A, B>scanLeft(fn, b).apply(as);
    }
}
