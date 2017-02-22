package contest.events.formulas;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public class EventFormulaTests {
    @Test
    public void abcValuesTest() {
        double testA = 1;
        double testB = 2;
        double testC = 3;
        new EventFormulaImpl(
                testA,
                testB,
                testC
        ) {
            @Override
            public int countScore(double performance) {
                fail("This action should not be called");

                // Something is wrong with Java :/
                // There must be a way to avoid this line without throwing AssertionError myself :/
                return 0;
            }

            @Override
            public double parsePoints(String humanReadablePoints) {
                return 0;
            }

            public void test() {
                assertEquals(this.a, testA, 0);
                assertEquals(this.b, testB, 0);
                assertEquals(this.c, testC, 0);
            }
        }.test();
    }
}
