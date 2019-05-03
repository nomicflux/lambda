package com.jnape.palatable.lambda.recursionschemes.builtin;

import com.jnape.palatable.lambda.adt.coproduct.CoProduct2;
import com.jnape.palatable.lambda.recursionschemes.Fix;
import org.junit.Test;
import testsupport.recursion.ListF;
import testsupport.recursion.NatF;

import java.util.function.Function;

import static com.jnape.palatable.lambda.adt.choice.Choice2.a;
import static com.jnape.palatable.lambda.adt.choice.Choice2.b;
import static com.jnape.palatable.lambda.recursionschemes.Fix.fix;
import static com.jnape.palatable.lambda.recursionschemes.builtin.Apomorphism.apo;
import static org.junit.Assert.assertEquals;
import static testsupport.recursion.ListF.cons;
import static testsupport.recursion.ListF.nil;

public class ApomorphismTest {

    @Test
    public void unfoldingToGreatestFixedPointWithShortCircuit() {
        Function<Integer, NatF<CoProduct2<Integer, Fix<NatF, NatF<Fix<NatF, ?>>>>>> nats =
                x -> NatF.s(x < 3 ? a(x + 1) : b(fix(NatF.z())));
        assertEquals(Fix.<NatF, NatF<Fix<NatF, ?>>>fix(NatF.s(fix(NatF.z()))), apo(nats, 3));
        assertEquals(Fix.<NatF, NatF<Fix<NatF, ?>>>fix(NatF.s(fix(NatF.s(fix(NatF.s(fix(NatF.z()))))))), apo(nats, 1));

        Function<Integer, ListF<String, CoProduct2<Integer, Fix<ListF<String, ?>, ListF<String, Fix<ListF<String, ?>, ?>>>>>> unfold =
                x -> cons("<" + x + ">", x < 3 ? a(x + 1) : b(fix(nil())));
        assertEquals(Fix.<ListF<String, ?>, ListF<String, Fix<ListF<String, ?>, ?>>>fix(cons("<1>", fix(cons("<2>", fix(cons("<3>", fix(nil()))))))), apo(unfold, 1));
        assertEquals(Fix.<ListF<String, ?>, ListF<String, Fix<ListF<String, ?>, ?>>>fix(cons("<3>", fix(nil()))), apo(unfold, 3));
    }
}