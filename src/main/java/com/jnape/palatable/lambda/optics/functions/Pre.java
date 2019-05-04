package com.jnape.palatable.lambda.optics.functions;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.specialized.Pure;
import com.jnape.palatable.lambda.functor.builtin.Const;
import com.jnape.palatable.lambda.optics.Optic;
import com.jnape.palatable.lambda.optics.ProtoOptic;

import static com.jnape.palatable.lambda.adt.Maybe.nothing;
import static com.jnape.palatable.lambda.optics.Optic.reframe;

/**
 * Turn an {@link Optic} with a unary mapping that can be used for viewing some number of values into an {@link Optic}
 * that views the first value, if it exists.
 *
 * @param <S> the value to read from
 * @param <T> used for unification of the {@link Optic optic's} unused morphism
 * @param <A> the result to {@link Maybe maybe} read out
 * @param <B> used for unification of the {@link Optic optic's} unused morphism
 */
public final class Pre<S, T, A, B> implements Fn1<Optic<? super Fn1<?, ?>, ? super Const<Maybe<A>, ?>, S, T, A, B>,
        Optic<Fn1<?, ?>, Const<Maybe<A>, ?>, S, T, Maybe<A>, B>> {

    private static final Pre<?, ?, ?, ?> INSTANCE = new Pre<>();

    private Pre() {
    }

    @Override
    public Optic<Fn1<?, ?>, Const<Maybe<A>, ?>, S, T, Maybe<A>, B> apply(
            Optic<? super Fn1<?, ?>, ? super Const<Maybe<A>, ?>, S, T, A, B> optic) {
        Optic<? super Fn1<?, ?>, ? super Const<Maybe<A>, ?>, S, T, Maybe<A>, B> mappedOptic = optic.mapA(Maybe::just);
        return reframe(mappedOptic);
    }

    @SuppressWarnings("unchecked")
    public static <S, T, A, B> Pre<S, T, A, B> pre() {
        return (Pre<S, T, A, B>) INSTANCE;
    }

    @SuppressWarnings("overloads")
    public static <S, T, A, B> Optic<Fn1<?, ?>, Const<Maybe<A>, ?>, S, T, Maybe<A>, B> pre(
            Optic<? super Fn1<?, ?>, ? super Const<Maybe<A>, ?>, S, T, A, B> optic) {
        return Pre.<S, T, A, B>pre().apply(optic);
    }

    @SuppressWarnings("overloads")
    public static <S, T, A, B> Optic<Fn1<?, ?>, Const<Maybe<A>, ?>, S, T, Maybe<A>, B> pre(
            ProtoOptic<? super Fn1<?, ?>, S, T, A, B> protoOptic) {
        Optic<? super Fn1<?, ?>, Const<Maybe<A>, ?>, S, T, A, B> optic = protoOptic
                .toOptic(new Pure<Const<Maybe<A>, ?>>() {
                    @Override
                    public <X> Const<Maybe<A>, X> apply(X x) {
                        return new Const<>(nothing());
                    }
                });
        return pre(optic);
    }
}