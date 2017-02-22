package io.in;

import contest.events.Event;
import contest.events.EventImpl;
import contest.events.formulas.EventFormula;
import contest.events.formulas.FieldEventFormula;
import contest.events.formulas.TimedEventFormula;
import io.FileReader;
import io.FileReaderImpl;
import logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public class EventReader extends FileReaderImpl<Event> {

    public EventReader(Optional<Logger> logger) {
        super(logger);
    }

    @Override
    public Stream<Event> read(InputStreamReader file) {
        BufferedReader reader = new BufferedReader(file);
        return reader.lines().map(s -> {
            String[] split = s.split(";");
            Optional<EventFormula> maybeFormula = getFormula(split);
            return (maybeFormula.isPresent()) ?
                    Optional.<Event>of(new EventImpl(split[1], maybeFormula.get())) :
                    Optional.<Event>empty();
        })
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    Optional<EventFormula> getFormula(String[] lineParts) {
        try {
            double a = Double.parseDouble(lineParts[2]);
            double b = Double.parseDouble(lineParts[3]);
            double c = Double.parseDouble(lineParts[4]);
            switch (lineParts[0]) {
                case "t":
                    return Optional.of(new TimedEventFormula(a, b, c));
                case "f":
                    return Optional.of(new FieldEventFormula(a, b, c));
                default:
                    logger.ifPresent(l -> l.logError(
                            "ERROR: first element cannot show formula's type: " + Arrays.toString(lineParts)));
            }
        } catch (NumberFormatException n) {
            logger.ifPresent(l -> l.logError(
                    "ERROR: " + "Array item 2,3 or 4 cannot be represented as number: " + Arrays.toString(lineParts) + "."));
        } catch (ArrayIndexOutOfBoundsException index) {
            logger.ifPresent(l -> l.logError(
                    "ERROR: Array has insufficient size: " + Arrays.toString(lineParts) + "."));
        }
        return Optional.empty();
    }

}
