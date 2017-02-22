package contest.winnerPlaces.structures;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public class RankRangeTests {
    private final int samplePlaceStartId = 1231231;
    private final int samplePlaceEndId = 1234;
    private final Rank sampleRank = new RankRange(samplePlaceStartId, samplePlaceEndId);

    @Test
    public void placeImplIsDerivedFromPlace() {
        assertThat(new RankRange(1, 1), instanceOf(Rank.class));
    }

    @Test
    public void singlePlaceTitleString() {
        assertThat(
                sampleRank.getRankTitle(),
                is(samplePlaceStartId + " - " + samplePlaceEndId));
    }
}
