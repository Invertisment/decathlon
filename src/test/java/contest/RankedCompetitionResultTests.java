package contest;

import contest.competitor.Competitor;
import contest.competitor.CompetitorImpl;
import contest.helpers.FakeCompetitionEntry;
import contest.winnerPlaces.structures.Rank;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public class RankedCompetitionResultTests {
    private final int sampleScore = 123;
    private final Competitor sampleCompetitor = new CompetitorImpl("John");
    private final Rank sampleRank = new Rank("123") {
        @Override
        public String getRankTitle() {
            throw new RuntimeException("This should not happen");
        }
    };

    @Test
    public void hasPosition() {
        RankedCompetitionResult result = new RankedCompetitionResult(new FakeCompetitionEntry(sampleScore, sampleCompetitor), sampleRank);
        assertThat(
                result.getRank(),
                is(sampleRank));
    }

    @Test
    public void nullPositionProducesNpe() {
        boolean gotNPEd = false;
        try {
            new RankedCompetitionResult(new FakeCompetitionEntry(sampleScore, sampleCompetitor), null);
        } catch (NullPointerException welcome) {
            gotNPEd = true;
        }
        assertThat(
                gotNPEd,
                is(true));
    }

    @Test
    public void extendsCompetitionResult() {
        RankedCompetitionResult result = new RankedCompetitionResult(new FakeCompetitionEntry(sampleScore, sampleCompetitor), sampleRank);
        assertThat(result, instanceOf(CompetitionResult.class));
    }

    @Test
    public void hasPositionInAuxConstructor() {
        RankedCompetitionResult result = new RankedCompetitionResult(new CompetitionResult(new FakeCompetitionEntry(sampleScore, sampleCompetitor)), sampleRank);
        assertThat(
                result.getRank(),
                is(sampleRank));
        assertThat(
                result.getCompetitionEntry().getCompetitor(),
                is(sampleCompetitor));
        assertThat(
                result.getScore(),
                is(sampleScore));
    }

    @Test
    public void nullPositionProducesNpeInAuxConstructor() {
        boolean gotNPEd = false;
        try {
            new RankedCompetitionResult(new FakeCompetitionEntry(sampleScore, sampleCompetitor), null);
        } catch (NullPointerException welcome) {
            gotNPEd = true;
        }
        assertThat(
                gotNPEd,
                is(true));
    }

}
