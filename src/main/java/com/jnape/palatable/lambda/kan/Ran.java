package com.jnape.palatable.lambda.kan;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.Functor;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Id.id;

public interface Ran<F extends Functor<?, F>, G extends Functor<?, G>, A> extends Functor<A, Ran<F, G, ?>> {
    default <R, FR extends Functor<R, F>, GR extends Functor<R, G>> Fn1<Fn1<? super A, ? extends FR>, ? extends GR> runRan() {
        return this::runRan;
    }

    <R, FR extends Functor<R, F>, GR extends Functor<R, G>> GR runRan(Fn1<? super A, ? extends FR> k);

    static <A, F extends Functor<?, F>, G extends Functor<?, G>, FA extends Functor<A, F>, GA extends Functor<A, G>> GA gran(Ran<F, G, FA> fa) {
        return fa.<A, FA, GA>runRan().apply(id());
    }

    @Override
    default <B> Ran<F, G, B> fmap(Fn1<? super A, ? extends B> fn) {
        return new Ran<F, G, B>() {

            @Override
            public <R, FR extends Functor<R, F>, GR extends Functor<R, G>> GR runRan(Fn1<? super B, ? extends FR> k) {
                return Ran.this.<R, FR, GR>runRan(fn.fmap(k));
            }
        };
    }
}
