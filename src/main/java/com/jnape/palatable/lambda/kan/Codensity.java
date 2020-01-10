package com.jnape.palatable.lambda.kan;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.recursion.RecursiveResult;
import com.jnape.palatable.lambda.functions.specialized.Pure;
import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.functor.Functor;
import com.jnape.palatable.lambda.functor.builtin.Lazy;
import com.jnape.palatable.lambda.monad.Monad;
import com.jnape.palatable.lambda.monad.MonadRec;

import static com.jnape.palatable.lambda.adt.Maybe.pureMaybe;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Id.id;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Repeat.repeat;
import static com.jnape.palatable.lambda.functions.builtin.fn2.Take.take;
import static com.jnape.palatable.lambda.functions.builtin.fn3.FoldLeft.foldLeft;
import static com.jnape.palatable.lambda.functions.recursion.RecursiveResult.terminate;
import static com.jnape.palatable.lambda.functions.recursion.Trampoline.trampoline;

public interface Codensity<F extends Functor<?, F>, A> extends
        Ran<F, F, A>,
        MonadRec<A, Codensity<F, ?>> {
    default <R, FR extends Functor<R, F>> Fn1<Fn1<? super A, ? extends FR>, ? extends FR> runCodensity() {
        return this::ran;
    }

    default <FA extends Functor<A, F>> FA lower(Pure<F> pure) {
        return this.<A, FA>runCodensity().apply(pure::apply);
    }

    static <F extends Functor<?, F>, A> Codensity<F, A> codensity(A a) {
        return new Codensity<F, A>() {

            @Override
            public <R, FR extends Functor<R, F>, FFR extends Functor<R, F>> FFR ran(Fn1<? super A, ? extends FR> k) {
                return k.apply(a).coerce();
            }
        };
    }

    @Override
    <R, FR extends Functor<R, F>, FFR extends Functor<R, F>> FFR ran(Fn1<? super A, ? extends FR> k);

    @Override
    default <B> Codensity<F, B> trampolineM(Fn1<? super A, ? extends MonadRec<RecursiveResult<A, B>, Codensity<F, ?>>> fn) {
        return new Codensity<F, B>() {

            @Override
            public <R, FR extends Functor<R, F>, FFR extends Functor<R, F>> FFR ran(Fn1<? super B, ? extends FR> k) {
                Fn1<? super A, ? extends RecursiveResult<A, B>> f = a -> {
                    Codensity<F, RecursiveResult<A, B>> apply = fn.apply(a).coerce();
                    RecursiveResult<A, B> o = null;
                    return o;
                };
                Fn1<? super A, ? extends FR> k1 = a -> {
                    Codensity<F, RecursiveResult<A, B>> apply = fn.apply(a).coerce();
                    Codensity<F, FR> fmap = apply.fmap(rr -> rr.match(r -> {
                                Codensity<F, RecursiveResult<A, B>> apply1 = fn.apply(r).coerce();
                                apply1.fmap(rr2 -> rr2.match(r2 -> null,
                                        t2 -> terminate(t2)));
                                Fn1<A, B> trampoline = trampoline(f);
                                FR o = trampoline.fmap(k).apply(r);
                                return o;
                            },
                            (B a1) -> k.apply(a1)));
                    FR o = fmap.ran(id());
                    return o;
                };
                return Codensity.this.<R, FR, FFR>ran(k1);
            }
        };
    }

    @Override
    default <B> Codensity<F, B> flatMap(Fn1<? super A, ? extends Monad<B, Codensity<F, ?>>> f) {
        return new Codensity<F, B>() {

            @Override
            public <R, FR extends Functor<R, F>, FFR extends Functor<R, F>> FFR ran(Fn1<? super B, ? extends FR> k) {
                return Codensity.this
                        .<R, FR, FFR>ran(f.fmap(mb -> mb.<Codensity<F, B>>coerce()
                                .fmap(k)
                                .<FFR>fmap(f -> f.<FFR>coerce())
                                .ran(id())))
                        .coerce();
            }
        };
    }

    @Override
    default <B> Codensity<F, B> pure(B b) {
        return codensity(b);
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
        return MonadRec.super.zip(appFn).coerce();
    }

    @Override
    default <B> Lazy<? extends Codensity<F, B>> lazyZip(Lazy<? extends Applicative<Fn1<? super A, ? extends B>, Codensity<F, ?>>> lazyAppFn) {
        return MonadRec.super.lazyZip(lazyAppFn).fmap(f -> {
            return f.<Codensity<F, B>>coerce();
        });
    }

    @Override
    default <B> Codensity<F, B> discardL(Applicative<B, Codensity<F, ?>> appB) {
        return MonadRec.super.discardL(appB).coerce();
    }

    @Override
    default <B> Codensity<F, A> discardR(Applicative<B, Codensity<F, ?>> appB) {
        return MonadRec.super.discardR(appB).coerce();
    }

    public static void main(String[] args) {
        Codensity<Maybe<?>, Integer> codensity = codensity(0);
        Codensity<Maybe<?>, Integer> maybeIntegerCodensity = foldLeft((cd, n) -> cd.flatMap(a -> codensity(a + n)), codensity, take(1000, repeat(1)));

        Maybe<Integer> integerMaybeFunctor = maybeIntegerCodensity.lower(pureMaybe());
        System.out.println(integerMaybeFunctor);
    }
}
