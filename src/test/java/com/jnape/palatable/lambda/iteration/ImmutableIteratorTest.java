package com.jnape.palatable.lambda.iteration;

import org.junit.Before;
import org.junit.Test;

import static testsupport.exceptions.OutOfScopeException.outOfScope;

public class ImmutableIteratorTest {

    private ImmutableIterator<?> immutableIterator;

    @Before
    public void setUp() {
        immutableIterator = new ImmutableIterator<Object>() {
            @Override
            public boolean hasNext() {
                throw outOfScope();
            }

            @Override
            public Object next() {
                throw outOfScope();
            }
        };
    }

    @Test(expected = UnsupportedOperationException.class)
    public void doesNotSupportRemove() {
        immutableIterator.remove();
    }
}
