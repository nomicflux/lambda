package com.jnape.palatable.lambda.functions.builtin.fn2;

import com.jnape.palatable.lambda.adt.product.Product3;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functions.Fn3;

/**
 * Given an <code>{@link Fn3}&lt;A, B, C, D&gt;</code> and a <code>{@link Product3}&lt;A, B, C&gt;</code>, destructure
 * the product and apply the slots as arguments to the function, returning the result.
 *
 * @param <A> the first argument type
 * @param <B> the second argument type
 * @param <C> the third argument type
 * @param <D> the result type
 */
public final class Into3<A, B, C, D> implements Fn2<Fn3<? super A, ? super B, ? super C, ? extends D>, Product3<A, B, C>, D> {

    private static final Into3<?,?,?,?> INSTANCE = new Into3<>();

    @Override
    public D apply(Fn3<? super A, ? super B, ? super C, ? extends D> fn, Product3<A, B, C> product) {
        return product.into(fn);
    }

    @SuppressWarnings("unchecked")
    public static <A, B, C, D> Into3<A, B, C, D> into3() {
        return (Into3<A, B, C, D>) INSTANCE;
    }

    public static <A, B, C, D> Fn1<Product3<A, B, C>, D> into3(Fn3<? super A, ? super B, ? super C, ? extends D> fn) {
        return Into3.<A, B, C, D>into3().apply(fn);
    }

    public static <A, B, C, D> D into3(Fn3<? super A, ? super B, ? super C, ? extends D> fn,
                                       Product3<A, B, C> product) {
        return Into3.<A, B, C, D>into3(fn).apply(product);
    }
}
