package io.in;

import contest.CompetitionEntry;
import contest.events.Event;
import contest.events.EventImpl;
import contest.events.formulas.EventFormula;
import io.FileReaderImpl;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public class CompetitionEntryReaderTests {

    @Test
    public void producesEntry() {
        String competitorName = "d98fub0";
        int count = 10;
        Event sampleEvent = new EventImpl(competitorName, new EventFormula() {
            @Override
            public int countScore(double performance) {
                return 1;
            }

            @Override
            public double parsePoints(String humanReadablePoints) throws IllegalArgumentException {
                throw new RuntimeException("This action is not allowed");
            }
        });
        FileReaderImpl<CompetitionEntry> reader = new CompetitionEntryReader(
                null,
                Stream.iterate(sampleEvent, e -> e)
                        .limit(count)
                        .collect(Collectors.toList()));
        String input = competitorName + ";1;1;1;1;1;1;1;1;1;1";

        Iterator<CompetitionEntry> read = reader.read(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes()))).iterator();

        CompetitionEntry entry = read.next();
        assertEquals(
                entry.getCompetitor().getName(),
                competitorName);
        assertEquals(
                entry.getPerformance().size(),
                count);
        assertFalse(read.hasNext());
    }

    @Test
    public void producesMultipleEntries() {
        String competitorName = "d98fujhbjkbb0";
        int eventCount = 5;
        Event sampleEvent = new EventImpl(competitorName, new EventFormula() {
            @Override
            public int countScore(double performance) {
                return 1;
            }

            @Override
            public double parsePoints(String humanReadablePoints) throws IllegalArgumentException {
                throw new RuntimeException("This action is not allowed");
            }
        });
        FileReaderImpl<CompetitionEntry> reader = new CompetitionEntryReader(
                null,
                Stream.iterate(sampleEvent, e -> e)
                        .limit(eventCount)
                        .collect(Collectors.toList()));
        int playerPerformanceSize = 5;
        String input = Stream.iterate(1, i -> i + 1)
                .limit(playerPerformanceSize)
                .map(operand -> Stream
                        .iterate(operand, o -> o)
                        .limit(eventCount)
                        .map(Object::toString))
                .map(stringStream ->
                        Stream.concat(
                                Stream.of(competitorName),
                                stringStream
                        ).collect(
                                Collectors.joining(";")))
                .collect(Collectors.joining("\n"));

        Iterator<CompetitionEntry> read = reader.read(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes()))).iterator();

        Stream.iterate(1, i -> i + 1)
                .limit(playerPerformanceSize)
                .forEach(integer -> {
                    CompetitionEntry entry = read.next();
                    assertEquals(
                            entry.getCompetitor().getName(),
                            competitorName);
                    assertEquals(
                            entry.getScore(),
                            playerPerformanceSize);
                });
        assertFalse(read.hasNext());
    }

    // TODO: test failures. (Bad input, InputStream IOExceptions, etc.)

}
