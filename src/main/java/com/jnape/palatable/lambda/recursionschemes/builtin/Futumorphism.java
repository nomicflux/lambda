package com.jnape.palatable.lambda.recursionschemes.builtin;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functions.builtin.fn1.Id;
import com.jnape.palatable.lambda.functor.Functor;
import com.jnape.palatable.lambda.recursionschemes.Coalgebra;
import com.jnape.palatable.lambda.recursionschemes.Fix;
import com.jnape.palatable.lambda.recursionschemes.Free;

import java.util.function.Function;

import static com.jnape.palatable.lambda.recursionschemes.Fix.fix;
import static com.jnape.palatable.lambda.recursionschemes.builtin.Anamorphism.ana;

public final class Futumorphism<A, F extends Functor, FR extends Functor<Free<A, F, ?>, F>>
//        implements Fn2<Fn1<A, FR>, A, Fix<F, ? extends Functor<? extends Fix<F, ?>, F>>> {
        implements Fn2<Fn1<A, FR>, A, Fix<F, ?>> {

    private static final Futumorphism INSTANCE = new Futumorphism();

    private Futumorphism() {
    }

    @Override
    @SuppressWarnings({"unchecked", "RedundantCast"})
//    public Fix<F, ? extends Functor<? extends Fix<F, ?>, F>> apply(Function<A, FR> fn, A a) {
//        return ana((Coalgebra<Free<A, F, ?>, FR>) free -> (FR) free.match(fn, id()), pure(a));
//    }
    //public static <A,
    //               F extends Functor,
    //               FA extends Functor<A, F>> Fix<F, Functor<Fix<F, ?>, F>> ana(
    //    Coalgebra<A, FA> coalgebra, A a) {
    //        return ana(coalgebra).apply(a);
    //    }
    //  <A, F extends Functor, FreeF extends Functor<? extends Free<A, F, ?>, F>>
    //  public <R> R match(Function<? super A, ? extends R> aFn, Function<? super FreeF, ? extends R> bFn)
    public Fix<F, Functor<Fix<F, Functor<Fix<F, ?>, F>>, F>> apply(Fn1<A, FR> fn, A a) {
        Coalgebra<A, Functor<A, F>> coalgebra = a1 -> {
            Functor<A, F> o = null;
            return o;
        };
        return Anamorphism.ana(coalgebra, a);
//        Coalgebra<A, Functor<A, F>> coalg = given -> {
//            Function<Free<A, F, ?>, A> fn1 = free1 -> {
//                Function<A, A> aFn = Id.id();
//                Function<Functor<? extends Free<A, F, ?>, F>, A> bFn = null;
//                return free1.<A>match(aFn, bFn);
//            };
//            Functor<A, F> o = fn.apply(given).fmap(fn1);
//            return o;
//        };
//        return Anamorphism.<A, F, Functor<A, F>>ana(coalg, a);
    }

    @SuppressWarnings("unchecked")
    public static <A, F extends Functor, FR extends Functor<Free<A, F, ?>, F>> Futumorphism<A, F, FR> futu() {
        return INSTANCE;
    }

    public static <A, F extends Functor, FR extends Functor<Free<A, F, ?>, F>> Fn1<A, Fix<F, ? extends Functor<? extends Fix<F, ?>, F>>> futu(
            Function<A, FR> fn) {
        return Futumorphism.<A, F, FR>futu().apply(fn);
    }

    public static <A, F extends Functor, FR extends Functor<Free<A, F, ?>, F>> Fix<F, ? extends Functor<? extends Fix<F, ?>, F>> futu(
            Function<A, FR> fn, A a) {
        return futu(fn).apply(a);
    }
}
