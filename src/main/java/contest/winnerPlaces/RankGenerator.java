package contest.winnerPlaces;

import contest.winnerPlaces.structures.Rank;

import java.util.stream.IntStream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-28
 */
interface RankGenerator {
    Rank getRank(int places);
}
