package testsupport.matchers;

import com.jnape.palatable.lambda.adt.Either;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.jnape.palatable.lambda.functions.Effect.fromConsumer;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;

public final class LeftMatcher<L, R> extends TypeSafeMatcher<Either<L, R>> {

    private final Matcher<L> lMatcher;

    private LeftMatcher(Matcher<L> lMatcher) {
        this.lMatcher = lMatcher;
    }

    @Override
    protected boolean matchesSafely(Either<L, R> actual) {
        return actual.match(lMatcher::matches, constantly(false));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Left value of ");
        lMatcher.describeTo(description);
    }

    @Override
    protected void describeMismatchSafely(Either<L, R> item, Description mismatchDescription) {
        mismatchDescription.appendText("was ");
        item.peek(fromConsumer(l -> {
                      mismatchDescription.appendText("Left value of ");
                      lMatcher.describeMismatch(l, mismatchDescription);
                  }),
                  fromConsumer(r -> mismatchDescription.appendValue(item)));
    }

    public static <L, R> LeftMatcher<L, R> isLeftThat(Matcher<L> lMatcher) {
        return new LeftMatcher<>(lMatcher);
    }
}
