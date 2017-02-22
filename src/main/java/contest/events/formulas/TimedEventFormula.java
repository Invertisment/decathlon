package contest.events.formulas;

import jdk.nashorn.internal.runtime.options.Option;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-24
 */
public class TimedEventFormula extends EventFormulaImpl {

    /**
     * @param a Score coefficient A
     * @param b Score coefficient B
     * @param c Score coefficient C
     */
    public TimedEventFormula(double a, double b, double c) {
        super(a, b, c);
    }

    /**
     * @param time Time in seconds with floating point precision
     * @see #EventFormulaImpl #getScore(double)
     */
    @Override
    public int countScore(double time) {
        return (int) (a * Math.pow(b - time, c)); // This cast is not needed in scala :'(
    }

    @Override
    public double parsePoints(String humanReadablePoints) {
        DateFormat format = new SimpleDateFormat("ss.SSS");
        try {
            return extractTime(format.parse(humanReadablePoints));
        } catch (ParseException ignored) {
        }
        format = new SimpleDateFormat("mm.ss.SSS");
        try {
            return extractTime(format.parse(humanReadablePoints));
        } catch (ParseException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }

    private double extractTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE) * 60 +
                cal.get(Calendar.SECOND) +
                cal.get(Calendar.MILLISECOND) / 100.0;

    }
}
