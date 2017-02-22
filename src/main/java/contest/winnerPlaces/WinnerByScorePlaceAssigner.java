package contest.winnerPlaces;

import contest.CompetitionResult;
import contest.RankedCompetitionResult;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Assigns winner places to given results
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public class WinnerByScorePlaceAssigner implements PlaceAssigner {
    @Override
    public Stream<RankedCompetitionResult> assignPlaces(Stream<CompetitionResult> results) {
        return sortMapEntries(
                splitEqualResults(results)
                        .entrySet().stream()
        ).map(new StatefulCompetitionPlaceFunction(
                new RankGeneratorImpl(Stream.iterate(1, i -> 1 + i))))
                .reduce(Stream::concat).orElse(Stream.empty());
    }

    /**
     * Splits results with equal scores into a Map of Streams
     */
    private Map<Integer, Stream<CompetitionResult>> splitEqualResults(Stream<CompetitionResult> results) {
        return results
                .collect(Collectors
                        .<CompetitionResult, Integer, Stream<CompetitionResult>>toMap(
                                CompetitionResult::getScore,
                                Stream::of,
                                Stream::concat));
    }

    private Stream<Stream<CompetitionResult>> sortMapEntries(Stream<Map.Entry<Integer, Stream<CompetitionResult>>> entryStream) {
        // Let's sort the entries from highest to lowest
        return entryStream
                .sorted((o1, o2) -> Integer
                        .compare(
                                o2.getKey(),
                                o1.getKey()))
                .map(Map.Entry::getValue);
    }

}

