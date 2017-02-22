package contest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import contest.competitor.Competitor;
import contest.competitor.CompetitorImpl;
import contest.competitor.Performance;
import contest.events.Event;
import contest.helpers.FakeCompetitionEntry;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
public class CompetitionResultTests {
    private final int sampleScore = 123;
    private final Competitor sampleCompetitor = new CompetitorImpl("John");

    @Test
    public void containsScore() {
        CompetitionResult result = new CompetitionResult(new FakeCompetitionEntry(sampleScore, sampleCompetitor));
        assertThat(
                result.getScore(),
                is(sampleScore));
    }

    @Test
    public void containsCompetitor() {
        CompetitionResult result = new CompetitionResult(new FakeCompetitionEntry(sampleScore, sampleCompetitor));
        assertThat(
                result.getCompetitionEntry().getCompetitor(),
                is(sampleCompetitor));
    }

    @Test
    public void npeTitleTest() {
        boolean gotNPEd = false;
        try {
            new CompetitionResult(new FakeCompetitionEntry(0, null));
        } catch (NullPointerException welcome) {
            gotNPEd = true;
        }
        assertThat(
                gotNPEd,
                is(true));
    }

    /**
     * Testing class because of lack of tuple class :(
     * Needed by #isComparable test
     *
     * @see #isComparable
     */
    private class Tuple implements Comparable<Tuple> { // Sorry, no inline classes in Java
        final String a;
        final int b;

        private Tuple(String a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Tuple o) {
            int scoreComparison = Integer.compare(o.b, b); // Reverse sorting
            if (scoreComparison == 0) {
                return a.compareTo(o.a);
            } else return scoreComparison;
        }
    }

    @Test
    public void isComparable() {
        Collection<Tuple> startingScores = Arrays.asList(
                new Tuple("a", 5),
                new Tuple("c", 4), // int is equal to 4th element
                new Tuple("a", 3),
                new Tuple("a", 4), // this one
                new Tuple("a", 1));

        Iterator<CompetitionResult> expected = startingScores.stream()
                .sorted()
                .map(tuple -> new CompetitionResult(new FakeCompetitionEntry(tuple.b, new CompetitorImpl(tuple.a))))
                .collect(Collectors.toList()).iterator();

        Iterator<CompetitionResult> results = startingScores.stream()
                .map(tuple -> new CompetitionResult(new FakeCompetitionEntry(tuple.b, new CompetitorImpl(tuple.a))))
                .sorted()
                .collect(Collectors.toList()).iterator();

        while (expected.hasNext() && results.hasNext()) {
            CompetitionResult expectedNext = expected.next();
            CompetitionResult resultNext = results.next();
            assertThat(resultNext.getCompetitionEntry().getCompetitor().compareTo(expectedNext.getCompetitionEntry().getCompetitor()), is(0));
            assertThat(resultNext.getScore(), is(expectedNext.getScore()));
        }
        assertFalse(expected.hasNext());
        assertFalse(results.hasNext());
    }


}
