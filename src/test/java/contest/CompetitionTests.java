package contest;

import contest.competitor.CompetitorImpl;
import contest.competitor.PerformanceImpl;
import contest.events.formulas.EventFormula;
import contest.helpers.FakeCompetitionEntry;
import contest.winnerPlaces.PlaceAssigner;
import contest.winnerPlaces.structures.Rank;
import contest.events.Event;
import contest.events.EventImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
public class CompetitionTests {
    private final Event sampleEvent = new EventImpl("", new EventFormula() {
        @Override
        public int countScore(double performance) {
            return 1;
        }

        @Override
        public double parsePoints(String humanReadablePoints) throws IllegalArgumentException {
            throw new RuntimeException("This action is not allowed");
        }
    });
    private final PlaceAssigner sampleAssigner = results -> null;

    @Test
    public void countCompetitionResult() {
        CompetitionEntry entry = new CompetitionEntry(new CompetitorImpl("Hans Jensen"),
                Collections.singletonList(new PerformanceImpl(12323, sampleEvent)));
        Collection<CompetitionEntry> entries = Collections.singletonList(entry);
        Competition competition = new Competition(entries, sampleAssigner);
        Stream<CompetitionResult> results = competition.getResults();

        Iterator<CompetitionResult> iterator = results.iterator();
        CompetitionResult result = iterator.next();
        assertFalse(iterator.hasNext());
        assertThat(result.getCompetitionEntry().getCompetitor(), is(entry.getCompetitor()));
        assertThat(result.getScore(), is(entry.getPerformance().iterator().next().countScore()));
        results.close();
    }

    @Test
    public void countManyCompetitionResults() {

        Collection<CompetitionEntry> entries = Arrays.asList(513, 663343, 1223)
                .stream()
                .map(i -> new CompetitionEntry(
                        new CompetitorImpl(i.toString()),
                        Collections.singletonList(new PerformanceImpl(
                                i,
                                sampleEvent)))).collect(Collectors.toList());

        Competition competition = new Competition(entries, sampleAssigner);
        Stream<CompetitionResult> results = competition.getResults();

        // Sadly there is no zip function in Java...
        Iterator<CompetitionResult> resultIterator = results.iterator();
        Iterator<CompetitionEntry> dataIterator = entries.iterator();
        while (resultIterator.hasNext() && dataIterator.hasNext()) {
            CompetitionResult result = resultIterator.next();
            CompetitionEntry data = dataIterator.next();
            assertThat(result.getCompetitionEntry().getCompetitor(), is(data.getCompetitor()));
            assertThat(result.getScore(), is(data.getScore()));
        }
        assertFalse("Result must not exceed data", resultIterator.hasNext());
        assertFalse("Data must not exceed result", dataIterator.hasNext());
        results.close();
    }

    @Test
    public void usesPlaceAssigner() {
        RankedCompetitionResult result = new RankedCompetitionResult(new FakeCompetitionEntry(1, new CompetitorImpl("John")), new Rank("test"));
        PlaceAssigner assigner = results -> Stream.of(result);
        Competition competition = new Competition(Collections.emptyList(), assigner);
        Collection<RankedCompetitionResult> results = competition.getRankedResults().collect(Collectors.toList());

        Iterator<RankedCompetitionResult> iterator = results.iterator();
        assertThat(
                iterator.next(),
                is(result));
        assertFalse(iterator.hasNext());
    }

}
