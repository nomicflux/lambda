package com.jnape.palatable.lambda.kan;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.adt.Unit;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.recursion.RecursiveResult;
import com.jnape.palatable.lambda.functions.specialized.Pure;
import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.functor.Functor;
import com.jnape.palatable.lambda.functor.builtin.Lazy;
import com.jnape.palatable.lambda.monad.Monad;
import com.jnape.palatable.lambda.monad.MonadRec;

import static com.jnape.palatable.lambda.adt.Maybe.pureMaybe;
import static com.jnape.palatable.lambda.adt.Unit.UNIT;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Id.id;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Repeat.repeat;
import static com.jnape.palatable.lambda.functions.builtin.fn2.Take.take;
import static com.jnape.palatable.lambda.functions.builtin.fn3.FoldLeft.foldLeft;
import static com.jnape.palatable.lambda.functions.recursion.RecursiveResult.terminate;
import static com.jnape.palatable.lambda.functions.recursion.Trampoline.trampoline;

public interface Codensity<F extends Functor<?, F>, A> extends MonadRec<A, Codensity<F, ?>> {
    default <R, FR extends Functor<R, F>> Fn1<Fn1<? super A, ? extends FR>, ? extends FR> runCodensity() {
        return this::runCodensity;
    }

    default <FA extends Functor<A, F>> FA lower(Pure<F> pure) {
        return this.<A, FA>runCodensity().apply(pure::apply);
    }

    default Ran<F, F, A> toRan() {
        return new Ran<F, F, A>() {

            @Override
            public <R, FR extends Functor<R, F>, FFR extends Functor<R, F>> FFR ran(Fn1<? super A, ? extends FR> k) {
                return Codensity.this.runCodensity(k).coerce();
            }
        };
    }

    static <R, F extends Functor<?, F>, FR extends Functor<R, F>> Codensity<F, Unit> wrapCodensity() {
        return new Codensity<F, Unit>() {
            @Override
            public <R, FR extends Functor<R, F>> FR runCodensity(Fn1<? super Unit, ? extends FR> k) {
                return k.apply(UNIT).coerce();
            }
        };
    }

    static <F extends Functor<?, F>, A> Codensity<F, A> fromRan(Ran<F, F, A> ran) {
        return new Codensity<F, A>() {

            @Override
            public <R, FR extends Functor<R, F>> FR runCodensity(Fn1<? super A, ? extends FR> k) {
                return ran.ran(k).coerce();
            }
        };
    }

    static <F extends Functor<?, F>, A> Codensity<F, A> codensity(A a) {
        return new Codensity<F, A>() {

            @Override
            public <R, FR extends Functor<R, F>> FR runCodensity(Fn1<? super A, ? extends FR> k) {
                return k.apply(a).coerce();
            }
        };
    }

    <R, FR extends Functor<R, F>> FR runCodensity(Fn1<? super A, ? extends FR> k);

    @Override
    default <B> Codensity<F, B> trampolineM(Fn1<? super A, ? extends MonadRec<RecursiveResult<A, B>, Codensity<F, ?>>> fn) {
        return new Codensity<F, B>() {

            @Override
            public <R, FR extends Functor<R, F>> FR runCodensity(Fn1<? super B, ? extends FR> k) {
                return null;
            }
        };
    }

    @Override
    default <B> Codensity<F, B> flatMap(Fn1<? super A, ? extends Monad<B, Codensity<F, ?>>> f) {
        return new Codensity<F, B>() {

            @Override
            public <R, FR extends Functor<R, F>> FR runCodensity(Fn1<? super B, ? extends FR> k) {
                return Codensity.this
                        .<R, FR>runCodensity(f.fmap(mb -> mb.<Codensity<F, B>>coerce()
                                .fmap(k)
                                .<FR>fmap(Functor::coerce)
                                .runCodensity(id())))
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
            public <R, FR extends Functor<R, F>> FR runCodensity(Fn1<? super B, ? extends FR> k) {
                return Codensity.this.runCodensity(fn.fmap(k));
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
