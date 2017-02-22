package contest.competitor;

import contest.events.Event;

/**
 * Combines Event and Contestant's points
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public interface Performance {

    double getPoints();

    Event getEvent();

    int countScore();
}
