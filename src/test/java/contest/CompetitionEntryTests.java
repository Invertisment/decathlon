package contest;

import contest.competitor.Competitor;
import contest.competitor.CompetitorImpl;
import contest.competitor.Performance;
import contest.competitor.PerformanceImpl;
import contest.events.Event;
import contest.events.EventImpl;
import contest.events.formulas.EventFormula;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public class CompetitionEntryTests {

    private final Competitor sampleCompetitor = new CompetitorImpl("John Doe");
    private final Collection<Performance> samplePerformance = Collections.singletonList(
            new PerformanceImpl(-1, new EventImpl("test drive", new EventFormula() {
                @Override
                public int countScore(double performance) {
                    throw new RuntimeException("This action is not allowed");
                }

                @Override
                public double parsePoints(String humanReadablePoints) throws IllegalArgumentException {
                    throw new RuntimeException("This action is not allowed");
                }
            })));

    @Test
    public void competitionEntryHasUser() {
        CompetitionEntry entry = new CompetitionEntry(sampleCompetitor, samplePerformance);
        assertThat(
                entry.getCompetitor(),
                is(sampleCompetitor));
    }

    @Test
    public void nullCompetitorGivesNpe() {
        boolean gotNPEd = false;
        try {
            new CompetitionEntry(null, samplePerformance);
        } catch (NullPointerException welcome) {
            gotNPEd = true;
        }
        assertThat(
                gotNPEd,
                is(true));
    }

    @Test
    public void competitionEntryHasPerformance() {
        CompetitionEntry entry = new CompetitionEntry(sampleCompetitor, samplePerformance);
        assertThat(
                entry.getPerformance(),
                is(samplePerformance));
    }

    @Test
    public void nullPerformanceGivesNpe() {
        boolean gotNPEd = false;
        try {
            new CompetitionEntry(sampleCompetitor, null);
        } catch (NullPointerException welcome) {
            gotNPEd = true;
        }
        assertThat(
                gotNPEd,
                is(true));
    }

    @Test
    public void countCompetitionEntryScore() {
        Event event = new EventImpl("event", new EventFormula() {
            @Override
            public int countScore(double performance) {
                return 12;
            }

            @Override
            public double parsePoints(String humanReadablePoints) throws IllegalArgumentException {
                throw new RuntimeException("This action is not allowed");
            }
        });
        Performance performance = new PerformanceImpl(500, event);
        Collection<Performance> performances =
                Stream.iterate(performance, p -> p).limit(5).collect(Collectors.toList());
        CompetitionEntry entry = new CompetitionEntry(sampleCompetitor, performances);
        assertThat(
                entry.getScore(),
                is(performances.stream()
                        .mapToInt(Performance::countScore)
                        .sum())
        );
    }

}
