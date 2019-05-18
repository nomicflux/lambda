package com.jnape.palatable.lambda.comonad;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.Functor;
import com.jnape.palatable.lambda.monad.Monad;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Id.id;

/**
 * Comonads are {@link Functor}s that support an {@link Comonad#extract()} operation
 * to yield a specific value, and an {@link Comonad#extend(Fn1)} which extends
 * a function which uses the global state of the Comonad to produce a new local result
 * at each point to yield a new Comonad.
 * <p>
 * Comonad laws:
 * <ul>
 * <li>left identity: <code>w.extend(wa -&gt; wa.extract()).equals(w)</code></li>
 * <li>right identity: <code>w.extend(f).extract().equals(a -&gt; f.apply(a))</code></li>
 * <li>associativity: <code>w.extend(f).extend(g).equals(w.extend(wa -&gt; g(wa.extend(f))))</code></li>
 * </ul>
 *
 * @param <A> the type of the value the Comonad stores
 * @param <W> the unification parameter to type-constrain Comonads to themselves (as an upside-down `M`, used for {@link Monad})
 */
public interface Comonad<A, W extends Comonad<?, W>> extends Functor<A, W> {

    /**
     * Extract an A from the Comonad.  Often Comonads feature some sort of cursor, and this will yield the element at the cursor.
     *
     * @return the current A
     */
    A extract();

    /**
     * Extend a function Fn&lt;Comonad&lt;A, W&gt;, B&gt; over a Comonad.  This allows for computations which use global knowledge to yield a local result.
     * <p>
     * For example, think of blurring an image, where the new pixel relies on the surrounding pixels, or of producing the next step in a generic cellular automaton.
     *
     * @param f      the function using the global state Comonad&lt;A, W&gt; to produce a B
     * @param <B>    the resulting B at each point in the resulting Comonad&lt;B, W&gt;
     * @return the new Comonad instance
     *
     * Default implementation in terms of duplicate would be `return this.duplicate().fmap(f);`
     *
     */
   <B> Comonad<B, W> extend(Fn1<? super Comonad<A, W>, ? extends B> f);

   /**
    * {@inheritDoc}
    */
   @Override
   default <B> Comonad<B, W> fmap(Fn1<? super A, ? extends B> fn) {
       return extend(wa -> fn.apply(wa.extract()));
   }

   /**
    * Duplicate a Comonad&lt;A, W&gt; to a Comonad&lt;Comonad&lt;A, W&gt;, W&gt;.
    * <p>
    * Essentially, for the case of a non-empty data structure with a cursor, this produces that data structure with the cursor at all possible locations.
    * <p>
    * It may be worth finding a way to do this lazily, as a Comonad can often be thought of as representing a state space (potentially infinite), with a Monad then used to traverse a specific path through it.
    * <p>
    * `extend` is left to be implemented, and `duplicate` is given a definition based off of it, in symmetry with `Monad`.
    * However, it might be better to reverse this for `Comonad`, as there may be a more natural definition for `duplicate` in an implementation for the interface.
    * <p>
    *
    *
    * @return the unfolded Comonad across all possible cursors
   */
   default Comonad<? super Comonad<A, W>, W> duplicate() {
       return this.extend(id());
   }
}

