package contest.competitor;

import contest.events.Event;
import contest.events.formulas.EventFormula;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
public class PerformanceTests {

    private final double dummyPoints = 5001;
    private final Event dummyEvent = new contest.events.EventImpl("evt", new EventFormula() {
        @Override
        public int countScore(double performance) {
            throw new RuntimeException("This calculation should not be executed");
        }

        @Override
        public double parsePoints(String humanReadablePoints) throws IllegalArgumentException {
            throw new RuntimeException("This calculation should not be executed");
        }
    });

    @Test
    public void getPerformancePointsTest() {
        Performance performance = new PerformanceImpl(dummyPoints, dummyEvent);
        assertThat(
                performance.getPoints(),
                is(dummyPoints));
    }

    @Test
    public void getEventTest() {
        PerformanceImpl performance = new PerformanceImpl(dummyPoints, dummyEvent);
        assertThat(
                performance.getEvent(),
                is(dummyEvent));
    }

    @Test
    public void eventNpeTest() {
        boolean gotNPEd = false;
        try {
            new PerformanceImpl(dummyPoints, null);
        } catch (NullPointerException welcome) {
            gotNPEd = true;
        }
        assertThat(
                gotNPEd,
                is(true));
    }

    @Test
    public void countScoreTest() {
        int performanceValue = 200;
        contest.events.EventImpl event = new contest.events.EventImpl("", new EventFormula() {
            @Override
            public int countScore(double performance) {
                return performanceValue;
            }

            @Override
            public double parsePoints(String humanReadablePoints) throws IllegalArgumentException {
                throw new RuntimeException("This calculation should not be executed");
            }
        });

        PerformanceImpl performance = new PerformanceImpl(500, event);
        assertEquals(
                performance.countScore(),
                event.getFormula().countScore(performanceValue)
        );
    }

}
