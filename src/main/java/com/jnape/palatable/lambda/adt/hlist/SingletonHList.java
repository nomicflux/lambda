package com.jnape.palatable.lambda.adt.hlist;

import com.jnape.palatable.lambda.adt.hlist.HList.HCons;
import com.jnape.palatable.lambda.adt.hlist.HList.HNil;
import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.functor.builtin.Lazy;
import com.jnape.palatable.lambda.monad.Monad;
import com.jnape.palatable.lambda.traversable.Traversable;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;

/**
 * A singleton HList. Supports random access.
 *
 * @param <_1> The single slot element type
 * @see HList
 * @see Tuple2
 * @see Tuple3
 * @see Tuple4
 * @see Tuple5
 */
public class SingletonHList<_1> extends HCons<_1, HNil> implements
        Monad<_1, SingletonHList<?>>,
        Comonad<_1, SingletonHList<?>>,
        Traversable<_1, SingletonHList<?>> {

    SingletonHList(_1 _1) {
        super(_1, nil());
    }

    @Override
    public <_0> Tuple2<_0, _1> cons(_0 _0) {
        return new Tuple2<>(_0, this);
    }

    @Override
    public _1 extract() {
        return head();
    }

    @Override
    public <B> Comonad<B, SingletonHList<?>> extendImpl(Fn1<? super Comonad<_1, SingletonHList<?>>, ? extends B> f) {
        return fmap(constantly(f.apply(this)));
    }

    @Override
    public <_1Prime> SingletonHList<_1Prime> fmap(Fn1<? super _1, ? extends _1Prime> fn) {
        return Monad.super.<_1Prime>fmap(fn).coerce();
    }

    @Override
    public <_1Prime> SingletonHList<_1Prime> pure(_1Prime _1Prime) {
        return singletonHList(_1Prime);
    }

    @Override
    public <_1Prime> SingletonHList<_1Prime> zip(
            Applicative<Fn1<? super _1, ? extends _1Prime>, SingletonHList<?>> appFn) {
        return Monad.super.zip(appFn).coerce();
    }

    @Override
    public <_1Prime> Lazy<SingletonHList<_1Prime>> lazyZip(
            Lazy<? extends Applicative<Fn1<? super _1, ? extends _1Prime>, SingletonHList<?>>> lazyAppFn) {
        return Monad.super.lazyZip(lazyAppFn).fmap(Monad<_1Prime, SingletonHList<?>>::coerce);
    }

    @Override
    public <_1Prime> SingletonHList<_1Prime> discardL(Applicative<_1Prime, SingletonHList<?>> appB) {
        return Monad.super.discardL(appB).coerce();
    }

    @Override
    public <_1Prime> SingletonHList<_1> discardR(Applicative<_1Prime, SingletonHList<?>> appB) {
        return Monad.super.discardR(appB).coerce();
    }

    @Override
    public <_1Prime> SingletonHList<_1Prime> flatMap(Fn1<? super _1, ? extends Monad<_1Prime, SingletonHList<?>>> f) {
        return f.apply(head()).coerce();
    }

    @Override
    public <B, App extends Applicative<?, App>, TravB extends Traversable<B, SingletonHList<?>>,
            AppTrav extends Applicative<TravB, App>> AppTrav traverse(Fn1<? super _1, ? extends Applicative<B, App>> fn,
                                                                      Fn1<? super TravB, ? extends AppTrav> pure) {
        return fn.apply(head()).fmap(SingletonHList::new).<TravB>fmap(Applicative::coerce).coerce();
    }

    /**
     * Apply {@link SingletonHList#head()} to <code>fn</code> and return the result.
     *
     * @param fn  the function to apply
     * @param <R> the return type of the function
     * @return the result of applying the head to the function
     */
    public <R> R into(Fn1<? super _1, ? extends R> fn) {
        return fn.apply(head());
    }
}
