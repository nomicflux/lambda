package com.jnape.palatable.lambda.comonad.builtin;

import com.jnape.palatable.lambda.comonad.Comonad;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.builtin.fn1.Id;
import com.jnape.palatable.lambda.functor.Functor;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Downcast.downcast;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Id.id;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Upcast.upcast;

public interface ComonadStore<S, A, W extends Comonad<?, W>> extends Comonad<A, W> {
    A peeks(Fn1<? super S, ? extends S> f);

    default A peek(S s) {
        return peeks(constantly(s));
    };

    default A pos() {
        return peeks(id());
    }

    ComonadStore<S, A, W> seeks(Fn1<? super S, ? extends S> f);

    // seeks can be implemented in terms of extend, but is not here to prevent cyclic reference should seeks be used to define extend for a given implementation
//    default <WA extends ComonadStore<S, A, W>> WA seeks(Fn1<? super S, ? extends S> f) {
//        return downcast(this.extend((Fn1<WA, A>) s -> s.peeks(f)));
//    };

    default ComonadStore<S, A, W> seek(S s) {
        return seeks(constantly(s));
    };

    //<F extends Functor<?, ? extends F>> Functor<A, ? extends F> experiment(Fn1<? super S, ? extends Functor<S, ? extends F>> f);
    <F extends Functor<?, F>> Functor<A, F> experiment(Fn1<? super S, ? extends Functor<S, F>> f);
    //<F extends Functor<?, ? extends F>, FA extends Functor<A, ? extends F>, FS extends Functor<S, ? extends S>> FA experiment(Fn1<? super S, ? extends FS> f);

    @Override
    default A extract() {
        return pos();
    }
}
