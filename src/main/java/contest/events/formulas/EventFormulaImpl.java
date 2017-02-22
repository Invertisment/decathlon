package contest.events.formulas;

/**
 * Performs score calculation from the given points
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-24
 */
public abstract class EventFormulaImpl implements EventFormula {

    /**
     * Score coefficient A
     */
    protected final double a;
    /**
     * Score coefficient B
     */
    protected final double b;
    /**
     * Score coefficient C
     */
    protected final double c;

    /**
     * @param a Score coefficient A
     * @param b Score coefficient B
     * @param c Score coefficient C
     */
    public EventFormulaImpl(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Calculates performance points for a given event (a,b,c coefficients)
     *
     * @param performance Performance value
     * @return Points that are derived from performance value
     */
    public abstract int countScore(double performance);
}
