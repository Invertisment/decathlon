package io.in;

import contest.events.Event;
import contest.events.formulas.EventFormula;
import contest.events.formulas.FieldEventFormula;
import contest.events.formulas.TimedEventFormula;
import logging.Logger;
import logging.LoggerImpl;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static junit.framework.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public class EventReaderTests {
    private final FakeFormula fakeFormula = new FakeFormula();

    private class FakeFormula implements EventFormula {
        @Override
        public int countScore(double performance) {
            return 0;
        }

        @Override
        public double parsePoints(String humanReadablePoints) {
            return 0;
        }
    }

    @Test
    public void readsSampleFile() throws FileNotFoundException {
        String input = "t;First event;1;2;3\nf;Second event;9;8;7.0";
        Iterator<String> expectedFormulaQueries = Arrays.asList(input.split("\n")).iterator();
        int[] formulaPassCount = new int[]{0};
        EventReader reader = new EventReader(Optional.<Logger>empty()) {
            @Override
            Optional<EventFormula> getFormula(String[] lineParts) {
                formulaPassCount[0]++;
                // Check the formula query
                assertTrue(
                        Arrays.equals(lineParts, expectedFormulaQueries.next().split(";"))
                );
                return Optional.of(fakeFormula);
            }
        };
        Collection<Event> collect = reader
                .read(new InputStreamReader(new ByteArrayInputStream(input.getBytes())))
                .collect(toList());
        assertThat(formulaPassCount[0], is(2));
        Iterator<Event> iterator = collect.iterator();
        Iterator<String> titles = Arrays.asList("First event", "Second event").iterator();
        for (int i = 0; i < 2; i++) {
            Event ev = iterator.next();
            assertEquals(
                    ev.getTitle(),
                    titles.next());
            assertThat(
                    ev.getFormula(),
                    is(fakeFormula));
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    public void producesTimedFormula() {
        String input = "t;First event;1;2;3";
        EventReader reader = new EventReader(Optional.<Logger>empty());
        EventFormula out = reader.getFormula(input.split(";")).get();
        EventFormula expected = new TimedEventFormula(1, 2, 3);
        assertThat(
                out,
                instanceOf(TimedEventFormula.class));
        assertEquals(
                out.countScore(9000),
                expected.countScore(9000));
    }

    @Test
    public void producesFieldFormula() {
        String input = "f;First event;1;2;3";
        EventReader reader = new EventReader(Optional.<Logger>empty());
        EventFormula output = reader.getFormula(input.split(";")).get();
        EventFormula expected = new FieldEventFormula(1, 2, 3);
        assertThat(
                output,
                instanceOf(FieldEventFormula.class));
        assertEquals(
                output.countScore(9000),
                expected.countScore(9000));
    }

    @Test
    public void failsOnBadTypeValueAndLogsIt() {
        String[] strings = new String[]{"a1231231", "1231231", "1231231", "1231231", "1231231", "1231231"};
        ByteArrayOutputStream byteArrStream = new ByteArrayOutputStream();
        PrintStream errStream = new PrintStream(byteArrStream);
        Logger logger = new LoggerImpl(errStream);
        EventReader reader = new EventReader(Optional.of(logger));
        Optional<EventFormula> out = reader.getFormula(strings);
        assertFalse(out.isPresent());
        assertThat(
                byteArrStream.toString(),
                is("ERROR: first element cannot show formula's type: " + Arrays.toString(strings)));
    }

    @Test
    public void failsOnBadDoubleValueAndLogsIt() {
        String[] strings = new String[]{"1231231", "1231231", "a1231231", "1231231", "1231231", "1231231"};
        ByteArrayOutputStream byteArrStream = new ByteArrayOutputStream();
        PrintStream errStream = new PrintStream(byteArrStream);
        Logger logger = new LoggerImpl(errStream);
        EventReader reader = new EventReader(Optional.of(logger));
        Optional<EventFormula> out = reader.getFormula(strings);
        assertFalse(out.isPresent());
        assertThat(
                byteArrStream.toString(),
                is("ERROR: Array item 2,3 or 4 cannot be represented as number: " + Arrays.toString(strings) + "."));
    }

    @Test
    public void failsOnInsufficientVariablesAndLogsIt() {
        String[] strings = new String[]{"1231231"};
        ByteArrayOutputStream byteArrStream = new ByteArrayOutputStream();
        PrintStream errStream = new PrintStream(byteArrStream);
        Logger logger = new LoggerImpl(errStream);
        EventReader reader = new EventReader(Optional.of(logger));
        Optional<EventFormula> out = reader.getFormula(strings);
        assertFalse(out.isPresent());
        assertThat(
                byteArrStream.toString(),
                is("ERROR: Array has insufficient size: " + Arrays.toString(strings) + "."));
    }
}
