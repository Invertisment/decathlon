package contest.winnerPlaces;

import contest.CompetitionResult;
import contest.RankedCompetitionResult;
import contest.winnerPlaces.structures.Rank;

import java.util.List;
import java.util.PrimitiveIterator;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Maps Stream of Streams of CompetitionResult to its positions with RankedCompetitionResult
 */
class StatefulCompetitionPlaceFunction implements Function<Stream<CompetitionResult>, Stream<RankedCompetitionResult>> {

    private final RankGenerator sampleRankGenerator;

    public StatefulCompetitionPlaceFunction(RankGenerator sampleRankGenerator) {
        this.sampleRankGenerator = sampleRankGenerator;
    }

    @Override
    public Stream<RankedCompetitionResult> apply(Stream<CompetitionResult> integerStreamEntry) {
        List<CompetitionResult> collection = integerStreamEntry.collect(toList());
        if (collection.isEmpty()) {
            return Stream.empty();
        }
        Rank rank = sampleRankGenerator.getRank(collection.size());

        return collection.stream()
                .map(result -> new RankedCompetitionResult(result, rank)); // TODO: cover this line
    }

}
