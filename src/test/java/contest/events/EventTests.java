package contest.events;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import contest.events.formulas.EventFormula;
import contest.events.formulas.EventFormulaImpl;
import org.junit.Test;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
public class EventTests {

    private final EventFormula dummyFormula = new EventFormula() {
        @Override
        public int countScore(double performance) {
            throw new RuntimeException("This calculation should not be executed");
        }

        @Override
        public double parsePoints(String humanReadablePoints) throws IllegalArgumentException {
            throw new RuntimeException("This calculation should not be executed");
        }
    };

    private final String dummyTitle = "sample text";

    @Test
    public void eventTitleTest() {
        Event event = new EventImpl(dummyTitle, dummyFormula);
        assertThat(
                event.getTitle(),
                is(dummyTitle));
    }

    @Test
    public void eventFormulaTest() {
        EventImpl event = new EventImpl(dummyTitle, dummyFormula);
        assertThat(
                event.getFormula(),
                is(dummyFormula));
    }

    @Test
    public void npeTitleTest() {
        boolean gotNPEd = false;
        try {
            new EventImpl(null, dummyFormula);
        } catch (NullPointerException welcome) {
            gotNPEd = true;
        }
        assertThat(
                gotNPEd,
                is(true));
    }

    @Test
    public void npeFormulaTest() {
        boolean gotNPEd = false;
        try {
            new EventImpl(dummyTitle, null);
        } catch (NullPointerException welcome) {
            gotNPEd = true;
        }
        assertThat(
                gotNPEd,
                is(true));
    }

}
