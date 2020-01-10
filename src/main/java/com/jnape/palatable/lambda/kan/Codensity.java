package com.jnape.palatable.lambda.kan;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.functor.Functor;
import com.jnape.palatable.lambda.functor.builtin.Lazy;
import com.jnape.palatable.lambda.monad.Monad;

public interface Codensity<F extends Functor<?, F>, A> extends
        Ran<F, F, A>,
        Monad<A, Codensity<F, ?>> {
    default <R, FR extends Functor<R, F>> Fn1<Fn1<? super A, ? extends FR>, ? extends FR> runCodensity() {
        return this::ran;
    }

    @Override
    <R, FR extends Functor<R, F>, FFR extends Functor<R, F>> FFR ran(Fn1<? super A, ? extends FR> k);

    @Override
    default <B> Codensity<F, B> flatMap(Fn1<? super A, ? extends Monad<B, Codensity<F, ?>>> f) {
        return new Codensity<F, B>() {

            @Override
            public <R, FR extends Functor<R, F>, FFR extends Functor<R, F>> FFR ran(Fn1<? super B, ? extends FR> k) {
                return Codensity.this
                        .<R, FR, FFR>ran(f.fmap(mb -> Ran.gran(mb.<Codensity<F, B>>coerce()
                                .fmap(k)
                                .<FFR>fmap(Functor::coerce))))
                        .coerce();
            }
        };
    }

    @Override
    default <B> Codensity<F, B> pure(B b) {
        return new Codensity<F, B>() {

            @Override
            public <R, FR extends Functor<R, F>, FFR extends Functor<R, F>> FFR ran(Fn1<? super B, ? extends FR> k) {
                return k.apply(b).coerce();
            }
        };
    }

    @Override
    default <B> Codensity<F, B> fmap(Fn1<? super A, ? extends B> fn) {
        return new Codensity<F, B>() {

            @Override
            public <R, FR extends Functor<R, F>, FFR extends Functor<R, F>> FFR ran(Fn1<? super B, ? extends FR> k) {
                return Codensity.this.ran(fn.fmap(k));
            }
        };
    }

    @Override
    default <B> Codensity<F, B> zip(Applicative<Fn1<? super A, ? extends B>, Codensity<F, ?>> appFn) {
        return Monad.super.zip(appFn).coerce();
    }

    @Override
    default <B> Lazy<? extends Codensity<F, B>> lazyZip(Lazy<? extends Applicative<Fn1<? super A, ? extends B>, Codensity<F, ?>>> lazyAppFn) {
        return Monad.super.lazyZip(lazyAppFn).fmap(Functor::coerce);
    }

    @Override
    default <B> Codensity<F, B> discardL(Applicative<B, Codensity<F, ?>> appB) {
        return Monad.super.discardL(appB).coerce();
    }

    @Override
    default <B> Codensity<F, A> discardR(Applicative<B, Codensity<F, ?>> appB) {
        return Monad.super.discardR(appB).coerce();
    }
}
