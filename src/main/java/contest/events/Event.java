package contest.events;

import contest.events.formulas.EventFormula;

/**
 * Describes single event
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
public interface Event {
    String getTitle();

    EventFormula getFormula();
}
