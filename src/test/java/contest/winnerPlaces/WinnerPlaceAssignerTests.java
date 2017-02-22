package contest.winnerPlaces;

import contest.CompetitionResult;
import contest.RankedCompetitionResult;
import contest.competitor.Competitor;
import contest.competitor.CompetitorImpl;
import contest.helpers.FakeCompetitionEntry;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public class WinnerPlaceAssignerTests {
    private final PlaceAssigner assigner = new WinnerByScorePlaceAssigner();

    @Test
    public void testSingleResult() {
        Stream<CompetitionResult> resultStream = Stream.of(new CompetitionResult(new FakeCompetitionEntry(1, new CompetitorImpl("John"))));
        Iterator<RankedCompetitionResult> finalResults = assigner.assignPlaces(resultStream).iterator();
        assertThat(
                finalResults.next().getRank().getRankTitle(),
                is("1"));
    }

    @Test
    public void testMultipleResults() {
        Competitor competitor = new CompetitorImpl("John");
        CompetitionResult winning = new CompetitionResult(new FakeCompetitionEntry(2, competitor));
        CompetitionResult losing = new CompetitionResult(new FakeCompetitionEntry(1, competitor));
        Stream<CompetitionResult> resultStream = Stream.of(losing, winning);
        Iterator<RankedCompetitionResult> finalResults = assigner.assignPlaces(resultStream).iterator();
        RankedCompetitionResult result = finalResults.next();
        assertThat(
                result.getRank().getRankTitle(),
                is("1"));
        assertThat(
                result.getScore(),
                is(2));
        result = finalResults.next();
        assertThat(
                result.getRank().getRankTitle(),
                is("2"));
        assertThat(
                result.getScore(),
                is(1));
        assertFalse(finalResults.hasNext());
    }

    @Test
    public void testResultConjunction() {
        Competitor competitor = new CompetitorImpl("John");
        Stream<CompetitionResult> resultStream =
                Arrays.asList(2, 3, 2, 1, 2).stream().map(i -> new CompetitionResult(new FakeCompetitionEntry(i, competitor)));
        Iterator<RankedCompetitionResult> finalResults = assigner.assignPlaces(resultStream).iterator();

        RankedCompetitionResult result = finalResults.next();
        assertThat(
                result.getRank().getRankTitle(),
                is("1"));
        assertThat(
                result.getScore(),
                is(3));

        result = finalResults.next();
        assertThat(
                result.getRank().getRankTitle(),
                is("2 - 4"));
        assertThat(
                result.getScore(),
                is(2));

        result = finalResults.next();
        assertThat(
                result.getRank().getRankTitle(),
                is("2 - 4"));
        assertThat(
                result.getScore(),
                is(2));

        result = finalResults.next();
        assertThat(
                result.getRank().getRankTitle(),
                is("2 - 4"));
        assertThat(
                result.getScore(),
                is(2));

        result = finalResults.next();
        assertThat(
                result.getRank().getRankTitle(),
                is("5"));
        assertThat(
                result.getScore(),
                is(1));

        assertFalse(finalResults.hasNext());
    }

}
