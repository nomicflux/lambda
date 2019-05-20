package com.jnape.palatable.lambda.functor.builtin;

import com.jnape.palatable.traitor.annotations.TestTraits;
import com.jnape.palatable.traitor.runners.Traits;
import org.junit.runner.RunWith;
import testsupport.traits.FunctorLaws;

import static com.jnape.palatable.lambda.functor.builtin.Env.env;

@RunWith(Traits.class)
public class EnvTest {

    @TestTraits({FunctorLaws.class})
    public Env<?, ?> testSubject() {
        return env("", "");
    }
}
