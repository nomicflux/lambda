package com.jnape.palatable.lambda.adt.hlist;

import com.jnape.palatable.lambda.adt.hlist.HList.HCons;
import com.jnape.palatable.lambda.adt.product.Product3;
import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.functor.Bifunctor;
import com.jnape.palatable.lambda.functor.builtin.Lazy;
import com.jnape.palatable.lambda.monad.Monad;
import com.jnape.palatable.lambda.traversable.Traversable;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;

/**
 * A 3-element tuple product type, implemented as a specialized HList. Supports random access.
 *
 * @param <_1> The first slot element type
 * @param <_2> The second slot element type
 * @param <_3> The third slot element type
 * @see Product3
 * @see HList
 * @see SingletonHList
 * @see Tuple2
 * @see Tuple4
 * @see Tuple5
 */
public class Tuple3<_1, _2, _3> extends HCons<_1, Tuple2<_2, _3>> implements
        Product3<_1, _2, _3>,
        Monad<_3, Tuple3<_1, _2, ?>>,
        Bifunctor<_2, _3, Tuple3<_1, ?, ?>>,
        Traversable<_3, Tuple3<_1, _2, ?>> {

    private final _1 _1;
    private final _2 _2;
    private final _3 _3;

    Tuple3(_1 _1, Tuple2<_2, _3> tail) {
        super(_1, tail);
        this._1 = _1;
        _2 = tail._1();
        _3 = tail._2();
    }

    @Override
    public <_0> Tuple4<_0, _1, _2, _3> cons(_0 _0) {
        return new Tuple4<>(_0, this);
    }

    @Override
    public _1 _1() {
        return _1;
    }

    @Override
    public _2 _2() {
        return _2;
    }

    @Override
    public _3 _3() {
        return _3;
    }

    @Override
    public Tuple3<_2, _3, _1> rotateL3() {
        return tuple(_2, _3, _1);
    }

    @Override
    public Tuple3<_3, _1, _2> rotateR3() {
        return tuple(_3, _1, _2);
    }

    @Override
    public Tuple3<_2, _1, _3> invert() {
        return tuple(_2, _1, _3);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <_3Prime> Tuple3<_1, _2, _3Prime> fmap(Fn1<? super _3, ? extends _3Prime> fn) {
        return (Tuple3<_1, _2, _3Prime>) Monad.super.fmap(fn);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <_2Prime> Tuple3<_1, _2Prime, _3> biMapL(Fn1<? super _2, ? extends _2Prime> fn) {
        return (Tuple3<_1, _2Prime, _3>) Bifunctor.super.biMapL(fn);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <_3Prime> Tuple3<_1, _2, _3Prime> biMapR(Fn1<? super _3, ? extends _3Prime> fn) {
        return (Tuple3<_1, _2, _3Prime>) Bifunctor.super.biMapR(fn);
    }

    @Override
    public <_2Prime, _3Prime> Tuple3<_1, _2Prime, _3Prime> biMap(Fn1<? super _2, ? extends _2Prime> lFn,
                                                                 Fn1<? super _3, ? extends _3Prime> rFn) {
        return new Tuple3<>(_1(), tail().biMap(lFn, rFn));
    }

    @Override
    public <_3Prime> Tuple3<_1, _2, _3Prime> pure(_3Prime _3Prime) {
        return tuple(_1, _2, _3Prime);
    }

    @Override
    public <_3Prime> Tuple3<_1, _2, _3Prime> zip(
            Applicative<Fn1<? super _3, ? extends _3Prime>, Tuple3<_1, _2, ?>> appFn) {
        return biMapR(appFn.<Tuple3<_1, _2, Fn1<? super _3, ? extends _3Prime>>>coerce()._3()::apply);
    }

    @Override
    public <_3Prime> Lazy<Tuple3<_1, _2, _3Prime>> lazyZip(
            Lazy<? extends Applicative<Fn1<? super _3, ? extends _3Prime>, Tuple3<_1, _2, ?>>> lazyAppFn) {
        return Monad.super.lazyZip(lazyAppFn).fmap(Monad<_3Prime, Tuple3<_1, _2, ?>>::coerce);
    }

    @Override
    public <_3Prime> Tuple3<_1, _2, _3Prime> discardL(Applicative<_3Prime, Tuple3<_1, _2, ?>> appB) {
        return Monad.super.discardL(appB).coerce();
    }

    @Override
    public <_3Prime> Tuple3<_1, _2, _3> discardR(Applicative<_3Prime, Tuple3<_1, _2, ?>> appB) {
        return Monad.super.discardR(appB).coerce();
    }

    @Override
    public <_3Prime> Tuple3<_1, _2, _3Prime> flatMap(
            Fn1<? super _3, ? extends Monad<_3Prime, Tuple3<_1, _2, ?>>> f) {
        return pure(f.apply(_3).<Tuple3<_1, _2, _3Prime>>coerce()._3);
    }

    @Override
    public <_3Prime, App extends Applicative<?, App>, TravB extends Traversable<_3Prime, Tuple3<_1, _2, ?>>,
            AppTrav extends Applicative<TravB, App>> AppTrav traverse(
            Fn1<? super _3, ? extends Applicative<_3Prime, App>> fn,
            Fn1<? super TravB, ? extends AppTrav> pure) {
        return fn.apply(_3).fmap(_3Prime -> fmap(constantly(_3Prime))).<TravB>fmap(Applicative::coerce).coerce();
    }

    /**
     * Given a value of type <code>A</code>, produced an instance of this tuple with each slot set to that value.
     *
     * @param a   the value to fill the tuple with
     * @param <A> the value type
     * @return the filled tuple
     * @see Tuple2#fill
     */
    public static <A> Tuple3<A, A, A> fill(A a) {
        return tuple(a, a, a);
    }
}
