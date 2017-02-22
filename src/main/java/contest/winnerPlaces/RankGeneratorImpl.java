package contest.winnerPlaces;

import contest.winnerPlaces.structures.Rank;
import contest.winnerPlaces.structures.RankImpl;
import contest.winnerPlaces.structures.RankRange;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-28
 */
class RankGeneratorImpl implements RankGenerator {
    private final Iterator<Integer> rankNumbers;

    public RankGeneratorImpl(Stream<Integer> rankNumbers) {
        this.rankNumbers = rankNumbers.iterator();
    }

    @Override
    public Rank getRank(int places) {
        int rangeStart = rankNumbers.next();
        if (places > 1) {
            for (int    i = 0; i < places - 2; i++) rankNumbers.next();
            return new RankRange(rangeStart, rankNumbers.next());
        }
        return new RankImpl(rangeStart);
    }
}
