import contest.Competition;
import contest.CompetitionEntry;
import contest.events.Event;
import contest.winnerPlaces.WinnerByScorePlaceAssigner;
import io.in.CompetitionEntryReader;
import io.in.EventReader;
import io.out.RankedCompetitionResultWriter;
import logging.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-30
 */
public class Main {
    private final Optional<Logger> logger;

    public Main(Optional<Logger> logger) {
        this.logger = logger;
    }

    public static void main(String[] args) {
        new Main(Optional.of(System.err::println)).run(args);
    }

    Optional<StringTuple> parseArgs(String[] args) {
        if (args.length != 2) return Optional.empty();
        return Optional.of(new StringTuple(args[0], args[1]));
    }

    void run(String[] args) {
        Optional<StringTuple> parsed = parseArgs(args);
        if (!parsed.isPresent()) {
            logger.ifPresent(l -> l.logError("ERROR: only two string file name arguments are accepted. Check your arguments."));
            return;
        }
        Optional<Collection<Event>> eventStream = readCompetitionData("events.txt")
                .map(eventStream1 -> eventStream1
                        .collect(Collectors.toList()));
        if (!eventStream.isPresent()) {
            logger.ifPresent(l -> l.logError("ERROR: events file is not present. (events.txt)"));
            return;
        }
        Optional<Stream<CompetitionEntry>> competitionEntryStream = readCompetitionEntries(parsed.get().in, eventStream.get());
        if (!competitionEntryStream.isPresent()) {
            logger.ifPresent(l -> l.logError("ERROR: could not load competition entries."));
            return;
        }
        Competition competition = new Competition(
                competitionEntryStream.get().collect(Collectors.toList()),
                new WinnerByScorePlaceAssigner());
        try {
            new RankedCompetitionResultWriter().write(
                    new OutputStreamWriter(
                            new FileOutputStream(
                                    new File(parsed.get().out))),
                    competition);
        } catch (FileNotFoundException e) {
            logger.ifPresent(l -> l.logError("ERROR: output file is not found. Given: " + parsed.get().out));
        }
    }

    Optional<Stream<Event>> readCompetitionData(String input) {
        try {
            return Optional.of(new EventReader(logger).read(new File(input)));
        } catch (FileNotFoundException e) {
            logger.ifPresent(l -> l.logError("ERROR: file could not be found"));
            e.printStackTrace(); // TODO: move exception logging into Logger class
        }
        return Optional.empty();
    }

    Optional<Stream<CompetitionEntry>> readCompetitionEntries(String inputFile, Collection<Event> events) {
        try {
            return Optional.of(new CompetitionEntryReader(logger, events).read(new File(inputFile)));
        } catch (FileNotFoundException e) {
            logger.ifPresent(l -> l.logError("ERROR: file could not be found"));
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
