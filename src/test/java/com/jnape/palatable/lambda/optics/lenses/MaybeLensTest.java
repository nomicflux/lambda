package com.jnape.palatable.lambda.optics.lenses;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.optics.Lens;
import org.junit.Before;
import org.junit.Test;

import static com.jnape.palatable.lambda.adt.Maybe.just;
import static com.jnape.palatable.lambda.adt.Maybe.nothing;
import static com.jnape.palatable.lambda.optics.Lens.lens;
import static com.jnape.palatable.lambda.optics.functions.Set.set;
import static com.jnape.palatable.lambda.optics.functions.View.view;
import static com.jnape.palatable.lambda.optics.lenses.MaybeLens.liftA;
import static com.jnape.palatable.lambda.optics.lenses.MaybeLens.liftB;
import static com.jnape.palatable.lambda.optics.lenses.MaybeLens.liftS;
import static com.jnape.palatable.lambda.optics.lenses.MaybeLens.liftT;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static testsupport.assertion.LensAssert.assertLensLawfulness;

public class MaybeLensTest {

    private Lens<String, Boolean, Character, Integer> lens;

    @Before
    public void setUp() {
        lens = lens(s -> s.charAt(0), (s, b) -> s.length() == b);
    }

    @Test
    public void asMaybeWrapsValuesInMaybe() {
        assertLensLawfulness(MaybeLens.asMaybe(),
                             asList(null, "foo"),
                             asList(nothing(), just("hi")));
    }

    @Test
    public void liftSLiftsSToMaybe() {
        assertEquals((Character) '3', view(liftS(lens, "3"), nothing()));
    }

    @Test
    public void liftTLiftsTToMaybe() {
        assertEquals(just(true), set(liftT(lens), 3, "123"));
    }

    @Test
    public void liftALiftsAToMaybe() {
        assertEquals(just('1'), view(liftA(lens), "123"));
    }

    @Test
    public void liftBLiftsBToMaybe() {
        assertEquals(true, set(MaybeLens.liftB(lens, 1), nothing(), "1"));
    }

    @Test
    public void unLiftSPullsSOutOfMaybe() {
        Lens<Maybe<String>, Maybe<Boolean>, Maybe<Character>, Maybe<Integer>> liftedToMaybe = liftS(liftT(liftA(liftB(lens, 3))), "123");
        assertEquals(just('f'), view(MaybeLens.unLiftS(liftedToMaybe), "f"));
    }

    @Test
    public void unLiftTPullsTOutOfMaybe() {
        Lens<Maybe<String>, Maybe<Boolean>, Maybe<Character>, Maybe<Integer>> liftedToMaybe = liftS(liftT(liftA(liftB(lens, 3))), "123");
        assertEquals(true, set(MaybeLens.unLiftT(liftedToMaybe, false), just(3), just("321")));
    }

    @Test
    public void unLiftAPullsAOutOfMaybe() {
        Lens<Maybe<String>, Maybe<Boolean>, Maybe<Character>, Maybe<Integer>> liftedToMaybe = liftS(liftT(liftA(liftB(lens, 3))), "123");
        assertEquals((Character) '1', view(MaybeLens.unLiftA(liftedToMaybe, '4'), nothing()));
    }

    @Test
    public void unLiftBPullsBOutOfMaybe() {
        Lens<Maybe<String>, Maybe<Boolean>, Maybe<Character>, Maybe<Integer>> liftedToMaybe = liftS(liftT(liftA(liftB(lens, 3))), "123");
        assertEquals(just(true), set(MaybeLens.unLiftB(liftedToMaybe), 3, just("321")));
    }
}