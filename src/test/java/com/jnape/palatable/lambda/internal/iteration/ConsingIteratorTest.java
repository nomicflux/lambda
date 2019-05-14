package com.jnape.palatable.lambda.internal.iteration;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static com.jnape.palatable.lambda.functions.builtin.fn2.Drop.drop;
import static com.jnape.palatable.lambda.functions.builtin.fn2.Iterate.iterate;
import static com.jnape.palatable.lambda.functions.builtin.fn2.Take.take;
import static com.jnape.palatable.lambda.functions.builtin.fn3.FoldRight.foldRight;
import static com.jnape.palatable.lambda.functor.builtin.Lazy.lazy;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConsingIteratorTest {

    private ConsingIterator<Integer> consingIterator;

    @Before
    public void setUp() {
        consingIterator = new ConsingIterator<>(0, asList(1, 2, 3));
    }

    @Test
    public void hasNextIfHeadNotYetIterated() {
        assertTrue(consingIterator.hasNext());
    }

    @Test
    public void nextIsHeadIfNotYetIterated() {
        assertEquals((Integer) 0, consingIterator.next());
    }

    @Test
    public void hasNextIfMoreElementsAfterHead() {
        consingIterator.next();
        assertTrue(consingIterator.hasNext());
    }

    @Test
    public void doesNotHaveNextIfNoElementsLeft() {
        consingIterator.next();
        consingIterator.next();
        consingIterator.next();
        consingIterator.next();
        assertFalse(consingIterator.hasNext());
    }

    @Test
    public void stackSafety() {
        Integer stackBlowingNumber = 10_000;
        Iterable<Integer> ints = foldRight((x, lazyAcc) -> lazyAcc.fmap(acc -> () -> new ConsingIterator<>(x, acc)),
                                           lazy((Iterable<Integer>) Collections.<Integer>emptyList()),
                                           take(stackBlowingNumber, iterate(x -> x + 1, 1)))
                .value();

        assertEquals(stackBlowingNumber,
                     take(1, drop(stackBlowingNumber - 1, ints)).iterator().next());
    }
}