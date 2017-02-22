package contest.events.formulas;

/**
 * Performs score calculation from the given points
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-24
 */
public interface EventFormula {

    /**
     * Calculates performance points for a given event (a,b,c coefficients)
     *
     * @param performance Performance value
     * @return Points that are derived from performance value
     */
    int countScore(double performance);

    double parsePoints(String humanReadablePoints) throws IllegalArgumentException;
}
