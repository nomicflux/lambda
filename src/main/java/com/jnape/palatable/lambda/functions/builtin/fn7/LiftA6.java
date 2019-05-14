package com.jnape.palatable.lambda.functions.builtin.fn7;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functions.Fn3;
import com.jnape.palatable.lambda.functions.Fn4;
import com.jnape.palatable.lambda.functions.Fn5;
import com.jnape.palatable.lambda.functions.Fn6;
import com.jnape.palatable.lambda.functions.Fn7;
import com.jnape.palatable.lambda.functor.Applicative;

/**
 * Lift into and apply an {@link Fn6} to six {@link Applicative} values, returning the result inside the same
 * {@link Applicative} context.
 *
 * @param <A>    the function's first argument type
 * @param <B>    the function's second argument type
 * @param <C>    the function's third argument type
 * @param <D>    the function's fourth argument type
 * @param <E>    the function's fifth argument type
 * @param <F>    the function's sixth argument type
 * @param <G>    the function's return type
 * @param <App>  the applicative unification type
 * @param <AppA> the inferred first applicative argument type
 * @param <AppB> the inferred second applicative argument type
 * @param <AppC> the inferred third applicative argument type
 * @param <AppD> the inferred fourth applicative argument type
 * @param <AppE> the inferred fifth applicative argument type
 * @param <AppF> the inferred sixth applicative argument type
 * @param <AppG> the inferred applicative return type
 * @see Applicative#zip(Applicative)
 */
public final class LiftA6<A, B, C, D, E, F, G,
        App extends Applicative<?, App>,
        AppA extends Applicative<A, App>,
        AppB extends Applicative<B, App>,
        AppC extends Applicative<C, App>,
        AppD extends Applicative<D, App>,
        AppE extends Applicative<E, App>,
        AppF extends Applicative<F, App>,
        AppG extends Applicative<G, App>> implements
        Fn7<Fn6<A, B, C, D, E, F, G>, AppA, AppB, AppC, AppD, AppE, AppF, AppG> {

    private static final LiftA6<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> INSTANCE = new LiftA6<>();

    private LiftA6() {
    }

    @Override
    public AppG apply(Fn6<A, B, C, D, E, F, G> fn, AppA appA, AppB appB, AppC appC, AppD appD, AppE appE,
                      AppF appF) {
        return appF.zip(appE.zip(appD.zip(appC.zip(appB.zip(appA.fmap(fn)))))).coerce();
    }


    @SuppressWarnings("unchecked")
    public static <A, B, C, D, E, F, G,
            App extends Applicative<?, App>,
            AppA extends Applicative<A, App>,
            AppB extends Applicative<B, App>,
            AppC extends Applicative<C, App>,
            AppD extends Applicative<D, App>,
            AppE extends Applicative<E, App>,
            AppF extends Applicative<F, App>,
            AppG extends Applicative<G, App>>
    LiftA6<A, B, C, D, E, F, G, App, AppA, AppB, AppC, AppD, AppE, AppF, AppG> liftA6() {
        return (LiftA6<A, B, C, D, E, F, G, App, AppA, AppB, AppC, AppD, AppE, AppF, AppG>) INSTANCE;
    }

    public static <A, B, C, D, E, F, G,
            App extends Applicative<?, App>,
            AppA extends Applicative<A, App>,
            AppB extends Applicative<B, App>,
            AppC extends Applicative<C, App>,
            AppD extends Applicative<D, App>,
            AppE extends Applicative<E, App>,
            AppF extends Applicative<F, App>,
            AppG extends Applicative<G, App>> Fn6<AppA, AppB, AppC, AppD, AppE, AppF, AppG> liftA6(
            Fn6<A, B, C, D, E, F, G> fn) {
        return LiftA6.<A, B, C, D, E, F, G, App, AppA, AppB, AppC, AppD, AppE, AppF, AppG>liftA6().apply(fn);
    }

    public static <A, B, C, D, E, F, G,
            App extends Applicative<?, App>,
            AppA extends Applicative<A, App>,
            AppB extends Applicative<B, App>,
            AppC extends Applicative<C, App>,
            AppD extends Applicative<D, App>,
            AppE extends Applicative<E, App>,
            AppF extends Applicative<F, App>,
            AppG extends Applicative<G, App>> Fn5<AppB, AppC, AppD, AppE, AppF, AppG> liftA6(
            Fn6<A, B, C, D, E, F, G> fn,
            AppA appA) {
        return LiftA6.<A, B, C, D, E, F, G, App, AppA, AppB, AppC, AppD, AppE, AppF, AppG>liftA6(fn).apply(appA);
    }

    public static <A, B, C, D, E, F, G,
            App extends Applicative<?, App>,
            AppA extends Applicative<A, App>,
            AppB extends Applicative<B, App>,
            AppC extends Applicative<C, App>,
            AppD extends Applicative<D, App>,
            AppE extends Applicative<E, App>,
            AppF extends Applicative<F, App>,
            AppG extends Applicative<G, App>> Fn4<AppC, AppD, AppE, AppF, AppG> liftA6(Fn6<A, B, C, D, E, F, G> fn,
                                                                                       AppA appA,
                                                                                       AppB appB) {
        return LiftA6.<A, B, C, D, E, F, G, App, AppA, AppB, AppC, AppD, AppE, AppF, AppG>liftA6(fn, appA).apply(appB);
    }

    public static <A, B, C, D, E, F, G,
            App extends Applicative<?, App>,
            AppA extends Applicative<A, App>,
            AppB extends Applicative<B, App>,
            AppC extends Applicative<C, App>,
            AppD extends Applicative<D, App>,
            AppE extends Applicative<E, App>,
            AppF extends Applicative<F, App>,
            AppG extends Applicative<G, App>> Fn3<AppD, AppE, AppF, AppG> liftA6(Fn6<A, B, C, D, E, F, G> fn, AppA appA,
                                                                                 AppB appB,
                                                                                 AppC appC) {
        return LiftA6.<A, B, C, D, E, F, G, App, AppA, AppB, AppC, AppD, AppE, AppF, AppG>liftA6(fn, appA, appB).apply(appC);
    }

    public static <A, B, C, D, E, F, G,
            App extends Applicative<?, App>,
            AppA extends Applicative<A, App>,
            AppB extends Applicative<B, App>,
            AppC extends Applicative<C, App>,
            AppD extends Applicative<D, App>,
            AppE extends Applicative<E, App>,
            AppF extends Applicative<F, App>,
            AppG extends Applicative<G, App>> Fn2<AppE, AppF, AppG> liftA6(Fn6<A, B, C, D, E, F, G> fn, AppA appA,
                                                                           AppB appB,
                                                                           AppC appC, AppD appD) {
        return LiftA6.<A, B, C, D, E, F, G, App, AppA, AppB, AppC, AppD, AppE, AppF, AppG>liftA6(fn, appA, appB, appC).apply(appD);
    }

    public static <A, B, C, D, E, F, G,
            App extends Applicative<?, App>,
            AppA extends Applicative<A, App>,
            AppB extends Applicative<B, App>,
            AppC extends Applicative<C, App>,
            AppD extends Applicative<D, App>,
            AppE extends Applicative<E, App>,
            AppF extends Applicative<F, App>,
            AppG extends Applicative<G, App>> Fn1<AppF, AppG> liftA6(Fn6<A, B, C, D, E, F, G> fn, AppA appA, AppB appB,
                                                                     AppC appC, AppD appD, AppE appE) {
        return LiftA6.<A, B, C, D, E, F, G, App, AppA, AppB, AppC, AppD, AppE, AppF, AppG>liftA6(fn, appA, appB, appC, appD).apply(appE);
    }

    public static <A, B, C, D, E, F, G,
            App extends Applicative<?, App>,
            AppA extends Applicative<A, App>,
            AppB extends Applicative<B, App>,
            AppC extends Applicative<C, App>,
            AppD extends Applicative<D, App>,
            AppE extends Applicative<E, App>,
            AppF extends Applicative<F, App>,
            AppG extends Applicative<G, App>> AppG liftA6(Fn6<A, B, C, D, E, F, G> fn, AppA appA, AppB appB,
                                                          AppC appC, AppD appD, AppE appE, AppF appF) {
        return LiftA6.<A, B, C, D, E, F, G, App, AppA, AppB, AppC, AppD, AppE, AppF, AppG>liftA6(fn, appA, appB, appC, appD, appE).apply(appF);
    }
}
