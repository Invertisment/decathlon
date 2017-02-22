package contest.winnerPlaces;

import org.junit.Test;

import java.util.Collection;
import java.util.PrimitiveIterator;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-28
 */
public class RankGeneratorTests {

    @Test
    public void producesSingleRanks() {
        RankGenerator generator = new RankGeneratorImpl(Stream.iterate(-100, i -> i + 1));
        IntStream.iterate(-100, i -> i + 1)
                .limit(5)
                .forEach(operand ->
                        assertEquals(
                                generator.getRank(1).getRankTitle(),
                                (valueOf(operand))));
    }

    @Test
    public void producesJoinedRanks() {
        IntUnaryOperator operator = i -> i + 1;
        int firstRangeElement = 560;
        Stream<Integer> input = IntStream.iterate(firstRangeElement, operator).boxed();
        PrimitiveIterator.OfInt expected = IntStream.iterate(firstRangeElement, operator).iterator();
        RankGenerator generator = new RankGeneratorImpl(input);
        int iterationStartValue = 2;
        IntStream.iterate(iterationStartValue, i -> i + 1)
                .limit(10)
                .forEach(operand -> {
                    int rangeStart = expected.nextInt();
                    for (int i = 0; i < operand - 2; i++) expected.nextInt();
                    int rangeEnd = expected.nextInt();
                    assertThat(
                            generator.getRank(operand).getRankTitle(),
                            is(String.valueOf(rangeStart + " - " + rangeEnd)));
                });
    }

}
