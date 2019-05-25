package com.jnape.palatable.lambda.adt.hlist;

import com.jnape.palatable.lambda.adt.hlist.HList.HCons;
import com.jnape.palatable.lambda.adt.product.Product8;
import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.functor.Bifunctor;
import com.jnape.palatable.lambda.functor.builtin.Lazy;
import com.jnape.palatable.lambda.monad.Monad;
import com.jnape.palatable.lambda.traversable.Traversable;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;

/**
 * An 8-element tuple product type, implemented as a specialized HList. Supports random access.
 *
 * @param <_1> The first slot element type
 * @param <_2> The second slot element type
 * @param <_3> The third slot element type
 * @param <_4> The fourth slot element type
 * @param <_5> The fifth slot element type
 * @param <_6> The sixth slot element type
 * @param <_7> The seventh slot element type
 * @param <_8> The eighth slot element type
 * @see Product8
 * @see HList
 * @see SingletonHList
 * @see Tuple2
 * @see Tuple3
 * @see Tuple4
 * @see Tuple5
 * @see Tuple6
 * @see Tuple7
 */
public class Tuple8<_1, _2, _3, _4, _5, _6, _7, _8> extends HCons<_1, Tuple7<_2, _3, _4, _5, _6, _7, _8>> implements
        Product8<_1, _2, _3, _4, _5, _6, _7, _8>,
        Monad<_8, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>>,
        Comonad<_8, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>>,
        Bifunctor<_7, _8, Tuple8<_1, _2, _3, _4, _5, _6, ?, ?>>,
        Traversable<_8, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>> {

    private final _1 _1;
    private final _2 _2;
    private final _3 _3;
    private final _4 _4;
    private final _5 _5;
    private final _6 _6;
    private final _7 _7;
    private final _8 _8;

    Tuple8(_1 _1, Tuple7<_2, _3, _4, _5, _6, _7, _8> tail) {
        super(_1, tail);
        this._1 = _1;
        _2 = tail._1();
        _3 = tail._2();
        _4 = tail._3();
        _5 = tail._4();
        _6 = tail._5();
        _7 = tail._6();
        _8 = tail._7();
    }

    @Override
    public <_0> HCons<_0, Tuple8<_1, _2, _3, _4, _5, _6, _7, _8>> cons(_0 _0) {
        return new HCons<>(_0, this);
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
    public _4 _4() {
        return _4;
    }

    @Override
    public _5 _5() {
        return _5;
    }

    @Override
    public _6 _6() {
        return _6;
    }

    @Override
    public _7 _7() {
        return _7;
    }

    @Override
    public _8 _8() {
        return _8;
    }

    @Override
    public Tuple8<_2, _3, _4, _5, _6, _7, _8, _1> rotateL8() {
        return tuple(_2, _3, _4, _5, _6, _7, _8, _1);
    }

    @Override
    public Tuple8<_8, _1, _2, _3, _4, _5, _6, _7> rotateR8() {
        return tuple(_8, _1, _2, _3, _4, _5, _6, _7);
    }

    @Override
    public Tuple8<_2, _3, _4, _5, _6, _7, _1, _8> rotateL7() {
        return tuple(_2, _3, _4, _5, _6, _7, _1, _8);
    }

    @Override
    public Tuple8<_7, _1, _2, _3, _4, _5, _6, _8> rotateR7() {
        return tuple(_7, _1, _2, _3, _4, _5, _6, _8);
    }

    @Override
    public Tuple8<_2, _3, _4, _5, _6, _1, _7, _8> rotateL6() {
        return tuple(_2, _3, _4, _5, _6, _1, _7, _8);
    }

    @Override
    public Tuple8<_6, _1, _2, _3, _4, _5, _7, _8> rotateR6() {
        return tuple(_6, _1, _2, _3, _4, _5, _7, _8);
    }

    @Override
    public Tuple8<_2, _3, _4, _5, _1, _6, _7, _8> rotateL5() {
        return tuple(_2, _3, _4, _5, _1, _6, _7, _8);
    }

    @Override
    public Tuple8<_5, _1, _2, _3, _4, _6, _7, _8> rotateR5() {
        return tuple(_5, _1, _2, _3, _4, _6, _7, _8);
    }

    @Override
    public Tuple8<_2, _3, _4, _1, _5, _6, _7, _8> rotateL4() {
        return tuple(_2, _3, _4, _1, _5, _6, _7, _8);
    }

    @Override
    public Tuple8<_4, _1, _2, _3, _5, _6, _7, _8> rotateR4() {
        return tuple(_4, _1, _2, _3, _5, _6, _7, _8);
    }

    @Override
    public Tuple8<_2, _3, _1, _4, _5, _6, _7, _8> rotateL3() {
        return tuple(_2, _3, _1, _4, _5, _6, _7, _8);
    }

    @Override
    public Tuple8<_3, _1, _2, _4, _5, _6, _7, _8> rotateR3() {
        return tuple(_3, _1, _2, _4, _5, _6, _7, _8);
    }

    @Override
    public Tuple8<_2, _1, _3, _4, _5, _6, _7, _8> invert() {
        return tuple(_2, _1, _3, _4, _5, _6, _7, _8);
    }

    @Override
    public _8 extract() {
        return _8();
    }

    @Override
    public <B> Comonad<B, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>> extendImpl(Fn1<? super Comonad<_8, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>>, ? extends B> f) {
        return fmap(constantly(f.apply(this)));
    }

    @Override
    public <_8Prime> Tuple8<_1, _2, _3, _4, _5, _6, _7, _8Prime> fmap(Fn1<? super _8, ? extends _8Prime> fn) {
        return Monad.super.<_8Prime>fmap(fn).coerce();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <_7Prime> Tuple8<_1, _2, _3, _4, _5, _6, _7Prime, _8> biMapL(Fn1<? super _7, ? extends _7Prime> fn) {
        return (Tuple8<_1, _2, _3, _4, _5, _6, _7Prime, _8>) Bifunctor.super.biMapL(fn);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <_8Prime> Tuple8<_1, _2, _3, _4, _5, _6, _7, _8Prime> biMapR(Fn1<? super _8, ? extends _8Prime> fn) {
        return (Tuple8<_1, _2, _3, _4, _5, _6, _7, _8Prime>) Bifunctor.super.biMapR(fn);
    }

    @Override
    public <_7Prime, _8Prime> Tuple8<_1, _2, _3, _4, _5, _6, _7Prime, _8Prime> biMap(
            Fn1<? super _7, ? extends _7Prime> lFn,
            Fn1<? super _8, ? extends _8Prime> rFn) {
        return new Tuple8<>(_1(), tail().biMap(lFn, rFn));
    }

    @Override
    public <_8Prime> Tuple8<_1, _2, _3, _4, _5, _6, _7, _8Prime> pure(_8Prime _8Prime) {
        return tuple(_1, _2, _3, _4, _5, _6, _7, _8Prime);
    }

    @Override
    public <_8Prime> Tuple8<_1, _2, _3, _4, _5, _6, _7, _8Prime> zip(
            Applicative<Fn1<? super _8, ? extends _8Prime>, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>> appFn) {
        return Monad.super.zip(appFn).coerce();
    }

    @Override
    public <_8Prime> Lazy<Tuple8<_1, _2, _3, _4, _5, _6, _7, _8Prime>> lazyZip(
            Lazy<? extends Applicative<Fn1<? super _8, ? extends _8Prime>,
                    Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>>> lazyAppFn) {
        return Monad.super.lazyZip(lazyAppFn).fmap(Monad<_8Prime, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>>::coerce);
    }

    @Override
    public <_8Prime> Tuple8<_1, _2, _3, _4, _5, _6, _7, _8Prime> discardL(
            Applicative<_8Prime, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>> appB) {
        return Monad.super.discardL(appB).coerce();
    }

    @Override
    public <_8Prime> Tuple8<_1, _2, _3, _4, _5, _6, _7, _8> discardR(
            Applicative<_8Prime, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>> appB) {
        return Monad.super.discardR(appB).coerce();
    }

    @Override
    public <_8Prime> Tuple8<_1, _2, _3, _4, _5, _6, _7, _8Prime> flatMap(
            Fn1<? super _8, ? extends Monad<_8Prime, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>>> f) {
        return pure(f.apply(_8).<Tuple8<_1, _2, _3, _4, _5, _6, _7, _8Prime>>coerce()._8());
    }

    @Override
    public <_8Prime, App extends Applicative<?, App>,
            TravB extends Traversable<_8Prime, Tuple8<_1, _2, _3, _4, _5, _6, _7, ?>>,
            AppTrav extends Applicative<TravB, App>> AppTrav traverse(
            Fn1<? super _8, ? extends Applicative<_8Prime, App>> fn,
            Fn1<? super TravB, ? extends AppTrav> pure) {
        return fn.apply(_8).fmap(_8Prime -> fmap(constantly(_8Prime))).<TravB>fmap(Applicative::coerce).coerce();
    }

    /**
     * Given a value of type <code>A</code>, produced an instance of this tuple with each slot set to that value.
     *
     * @param a   the value to fill the tuple with
     * @param <A> the value type
     * @return the filled tuple
     * @see Tuple2#fill
     */
    public static <A> Tuple8<A, A, A, A, A, A, A, A> fill(A a) {
        return tuple(a, a, a, a, a, a, a, a);
    }
}
