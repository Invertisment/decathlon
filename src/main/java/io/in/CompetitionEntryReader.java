package io.in;

import contest.CompetitionEntry;
import contest.competitor.CompetitorImpl;
import contest.competitor.Performance;
import contest.competitor.PerformanceImpl;
import contest.events.Event;
import io.FileReaderImpl;
import logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public class CompetitionEntryReader extends FileReaderImpl<CompetitionEntry> {

    private final Collection<Event> events;

    public CompetitionEntryReader(Optional<Logger> logger, Collection<Event> events) {
        super(logger);
        this.events = events;
    }

    @Override
    public Stream<CompetitionEntry> read(InputStreamReader file) {
        BufferedReader reader = new BufferedReader(file);
        return reader.lines().map(s -> {
            String[] segments = s.split(";");
            Iterator<String> eventPerformances = Arrays.asList(segments)
                    .stream()
                    .skip(1)
                    .iterator();
            Collection<Performance> performances = events.stream()
                    .map(event -> {
                        Double performance = event.getFormula().parsePoints(eventPerformances.next());
                        return new PerformanceImpl(performance, event);
                    }).collect(Collectors.toList());
            return new CompetitionEntry(new CompetitorImpl(segments[0]), performances);
        });
    }
}
