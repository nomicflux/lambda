package com.jnape.palatable.lambda.functor.builtin;

import com.jnape.palatable.lambda.adt.Unit;
import com.jnape.palatable.lambda.adt.hlist.HList;
import com.jnape.palatable.lambda.adt.hlist.Tuple2;
import com.jnape.palatable.lambda.adt.product.Product2;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.monad.Monad;

import java.util.function.Function;

import static com.jnape.palatable.lambda.adt.Unit.UNIT;
import static com.jnape.palatable.lambda.adt.hlist.HList.tuple;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Id.id;
import static com.jnape.palatable.lambda.functions.builtin.fn2.Both.both;
import static com.jnape.palatable.lambda.functions.builtin.fn2.Into.into;

/**
 * The state {@link Monad}, useful for iteratively building up state and state-contextualized result.
 * <p>
 * For more information, read about the
 * <a href="https://en.wikibooks.org/wiki/Haskell/Understanding_monads/State" target="_blank">state monad</a>.
 *
 * @param <S> the state type
 * @param <A> the result type
 */
public final class State<S, A> implements Monad<A, State<S, ?>> {

    private final Fn1<? super S, ? extends Tuple2<A, S>> stateFn;

    private State(Fn1<? super S, ? extends Tuple2<A, S>> stateFn) {
        this.stateFn = stateFn;
    }

    /**
     * Run the stateful computation, returning a {@link Tuple2} of the result and the final state.
     *
     * @param s the initial state
     * @return a {@link Tuple2} of the result and the final state.
     */
    public Tuple2<A, S> run(S s) {
        return stateFn.apply(s).into(HList::tuple);
    }

    /**
     * Run the stateful computation, returning the result.
     *
     * @param s the initial state
     * @return the result
     */
    public A eval(S s) {
        return run(s)._1();
    }

    /**
     * Run the stateful computation, returning the final state.
     *
     * @param s the initial state
     * @return the final state
     */
    public S exec(S s) {
        return run(s)._2();
    }

    /**
     * Map both the result and the final state to a new result and final state.
     *
     * @param fn  the mapping function
     * @param <B> the potentially new final state type
     * @return the mapped {@link State}
     */
    public <B> State<S, B> mapState(Fn1<? super Tuple2<A, S>, ? extends Product2<B, S>> fn) {
        return state(s -> fn.apply(run(s)));
    }

    /**
     * Map the final state to a new final state using the provided function.
     *
     * @param fn the state-mapping function
     * @return the mapped {@link State}
     */
    public State<S, A> withState(Fn1<? super S, ? extends S> fn) {
        return state(s -> run(fn.apply(s)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> State<S, B> flatMap(Function<? super A, ? extends Monad<B, State<S, ?>>> f) {
        return state(s -> run(s).into((a, s2) -> f.apply(a).<State<S, B>>coerce().run(s2)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> State<S, B> pure(B b) {
        return state(s -> tuple(b, s));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> State<S, B> fmap(Function<? super A, ? extends B> fn) {
        return Monad.super.<B>fmap(fn).coerce();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> State<S, B> zip(Applicative<Function<? super A, ? extends B>, State<S, ?>> appFn) {
        return Monad.super.zip(appFn).coerce();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> Lazy<State<S, B>> lazyZip(
            Lazy<? extends Applicative<Function<? super A, ? extends B>, State<S, ?>>> lazyAppFn) {
        return Monad.super.lazyZip(lazyAppFn).fmap(Monad<B, State<S, ?>>::coerce);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> State<S, A> discardR(Applicative<B, State<S, ?>> appB) {
        return Monad.super.discardR(appB).coerce();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <B> State<S, B> discardL(Applicative<B, State<S, ?>> appB) {
        return Monad.super.discardL(appB).coerce();
    }

    /**
     * Create a {@link State} that simply returns back the initial state as both the result and the final state
     *
     * @param <A> the state and result type
     * @return the new {@link State} instance
     */
    @SuppressWarnings("RedundantTypeArguments")
    public static <A> State<A, A> get() {
        return new State<>(Tuple2::<A>fill);
    }

    /**
     * Create a {@link State} that ignores its initial state, returning a {@link Unit} result and <code>s</code> as its
     * final state.
     *
     * @param s   the final state
     * @param <S> the state type
     * @return the new {@link State} instance
     */
    public static <S> State<S, Unit> put(S s) {
        return new State<>(constantly(tuple(UNIT, s)));
    }

    /**
     * Create a {@link State} that maps its initial state into its result, but leaves the initial state unchanged.
     *
     * @param fn  the mapping function
     * @param <S> the state type
     * @param <A> the result type
     * @return the new {@link State} instance
     */
    public static <S, A> State<S, A> gets(Fn1<? super S, ? extends A> fn) {
        return state(both(fn, id()));
    }

    /**
     * Create a {@link State} that maps its initial state into its final state, returning a {@link Unit} result type.
     *
     * @param fn  the mapping function
     * @param <S> the state type
     * @return the new {@link State} instance
     */
    public static <S> State<S, Unit> modify(Fn1<? super S, ? extends S> fn) {
        return state(both(constantly(UNIT), fn));
    }

    /**
     * Create a {@link State} from <code>stateFn</code>, a function that maps an initial state into a result and a final
     * state.
     *
     * @param stateFn the state function
     * @param <S>     the state type
     * @param <A>     the result type
     * @return the new {@link State} instance
     */
    public static <S, A> State<S, A> state(Fn1<? super S, ? extends Product2<A, S>> stateFn) {
        return new State<>(stateFn.fmap(into(HList::tuple)));
    }

    /**
     * Create a {@link State} that returns <code>a</code> as its result and its initial state as its final state.
     *
     * @param a   the result
     * @param <S> the state type
     * @param <A> the result type
     * @return the new {@link State} instance
     */
    public static <S, A> State<S, A> state(A a) {
        return gets(constantly(a));
    }
}
