package contest.events.formulas;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-24
 */
public class FieldEventFormula extends EventFormulaImpl {
    /**
     * @param a Score coefficient A
     * @param b Score coefficient B
     * @param c Score coefficient C
     */
    public FieldEventFormula(double a, double b, double c) {
        super(a, b, c);
    }

    /**
     * @param playerPerformance Performance in a field event
     * @see #EventFormulaImpl #getScore(double)
     */
    @Override
    public int countScore(double playerPerformance) {
        return (int) (a * Math.pow(playerPerformance - b, c));
    }

    @Override
    public double parsePoints(String humanReadablePoints) {
        return Double.parseDouble(humanReadablePoints);
    }
}
