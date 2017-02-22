package contest.competitor;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
public class CompetitorTests {

    @Test
    public void getNameTest() {
        String dummyName = "Golden boy";
        Competitor competitor = new CompetitorImpl(dummyName);
        assertThat(
                competitor.getName(),
                is(dummyName));
    }

    @Test
    public void nameNullTest() {
        boolean gotNPEd = false;
        try {
            new CompetitorImpl(null);
        } catch (NullPointerException welcome) {
            gotNPEd = true;
        }
        assertThat(
                gotNPEd,
                is(true));
    }

    @Test
    public void sortingComparatorTest() {
        Collection<String> abc = Arrays.asList("C", "B", "A");
        assertThat(
                Arrays.equals(
                        abc.stream().map(CompetitorImpl::new).sorted().map(CompetitorImpl::getName).toArray(),
                        abc.stream().sorted().map(CompetitorImpl::new).map(CompetitorImpl::getName).toArray()),
                is(true));
    }

}
