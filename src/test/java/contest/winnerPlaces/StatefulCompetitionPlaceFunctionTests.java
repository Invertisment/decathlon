package contest.winnerPlaces;

import com.sun.istack.internal.NotNull;
import contest.CompetitionEntry;
import contest.CompetitionResult;
import contest.RankedCompetitionResult;
import contest.competitor.Competitor;
import contest.competitor.CompetitorImpl;
import contest.competitor.Performance;
import contest.events.Event;
import contest.helpers.FakeCompetitionEntry;
import contest.winnerPlaces.structures.Rank;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * Maps Stream of Streams of CompetitionResult to its positions with RankedCompetitionResult
 */
public class StatefulCompetitionPlaceFunctionTests {
    private final Rank sampleRank = new Rank("rank");
    private final RankGenerator sampleRankGenerator = places -> sampleRank;
    private final Competitor sampleCompetitor = new Competitor() {
        @Override
        public String getName() {
            return "name";
        }

        @Override
        public int compareTo(@NotNull Competitor o) {
            return 0;
        }
    };

    @Test
    public void handlesSingleElement() {
        StatefulCompetitionPlaceFunction function = new StatefulCompetitionPlaceFunction(sampleRankGenerator);
        CompetitionResult inputItem = new CompetitionResult(new FakeCompetitionEntry(1, sampleCompetitor));
        Iterator<RankedCompetitionResult> out = function.apply(Stream.of(inputItem)).iterator();
        RankedCompetitionResult outNext = out.next();
        assertThat(outNext.getRank(), is(sampleRank));
        assertThat(outNext.getScore(), is(1));
    }

    @Test
    public void handlesMultipleElements() {
        Competitor competitor = new CompetitorImpl("John");
        int score = 12355;
        Stream<CompetitionResult> input = Stream.generate(() -> score).limit(5).map(i ->
                new CompetitionResult(new FakeCompetitionEntry(i, competitor)));
        StatefulCompetitionPlaceFunction function = new StatefulCompetitionPlaceFunction(sampleRankGenerator);
        Iterator<RankedCompetitionResult> finalResults = function
                .apply(input)
                .iterator();
        for (int i = 0; i < 5; i++) {
            RankedCompetitionResult result = finalResults.next();
            assertThat(
                    result.getRank(),
                    is(sampleRank));
            assertThat(
                    result.getScore(),
                    is(score));
        }

        assertFalse(finalResults.hasNext());
    }

    @Test
    public void handlesEmpty() {
        StatefulCompetitionPlaceFunction function = new StatefulCompetitionPlaceFunction(sampleRankGenerator);
        Iterator<RankedCompetitionResult> finalResults = function
                .apply(Stream.empty())
                .iterator();
        assertFalse(finalResults.hasNext());
    }
}
