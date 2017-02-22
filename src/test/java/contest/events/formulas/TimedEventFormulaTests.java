package contest.events.formulas;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.stream.IntStream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-24
 */
public class TimedEventFormulaTests {
    @Test
    public void formulaTest() {
        IntStream
                .range(5, 6)
                .forEach(a -> IntStream
                        .range(5, 6)
                        .forEach(b -> IntStream
                                .range(5, 6)
                                .forEach(c -> {
                                    TimedEventFormula formula = new TimedEventFormula(a, b, c);
                                    IntStream.range(18, 20).forEach(
                                            performance -> {
                                                int score = formula.countScore(performance);
                                                Assert.assertThat(score, Is.is(
                                                        (int) (a * Math.pow(b - performance, c))
                                                ));
                                            });
                                })));
    }

    @Test
    public void parsesMinutesAndSeconds() throws ParseException {
        TimedEventFormula formula = new TimedEventFormula(0, 0, 0);
        DateFormat format = new SimpleDateFormat("mm.ss.SSS");
        double out = formula.parsePoints("03.53.79");

        Calendar expected = Calendar.getInstance();
        expected.setTime(format.parse("03.53.79"));

        Assert.assertEquals(
                out,
                expected.get(Calendar.MINUTE) * 60 +
                        expected.get(Calendar.SECOND) +
                        expected.get(Calendar.MILLISECOND) / 100.0,
                1E-100);
    }

}
