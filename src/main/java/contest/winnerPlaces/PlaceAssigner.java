package contest.winnerPlaces;

import contest.CompetitionResult;
import contest.RankedCompetitionResult;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Assigns winner places to given results
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public interface PlaceAssigner {
    Stream<RankedCompetitionResult> assignPlaces(Stream<CompetitionResult> results);
}
