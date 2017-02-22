package contest.events.formulas;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-24
 */
public class FieldEventFormulaTests {
    @Test
    public void formulaTest() {
        IntStream
                .range(5, 6)
                .forEach(a -> IntStream
                        .range(5, 6)
                        .forEach(b -> IntStream
                                .range(5, 6)
                                .forEach(c -> {
                                    FieldEventFormula formula = new FieldEventFormula(a, b, c);
                                    IntStream.range(18, 20).forEach(
                                            performance -> {
                                                int score = formula.countScore(performance);
                                                Assert.assertThat(score, Is.is(
                                                        (int) (a * Math.pow(performance - b, c))
                                                ));
                                            });
                                })));
    }

    @Test
    public void parsesDouble() {
        Double input = 1.325;
        FieldEventFormula formula = new FieldEventFormula(0,0,0);
        double out = formula.parsePoints(String.valueOf(input));
        Assert.assertEquals(out, input, 0);
    }

}
