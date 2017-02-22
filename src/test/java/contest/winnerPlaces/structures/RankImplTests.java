package contest.winnerPlaces.structures;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public class RankImplTests {

    private final int sampleRankId = 1;
    private final RankImpl sampleRank = new RankImpl(sampleRankId);

    @Test
    public void placeImplIsDerivedFromPlace() {
        assertThat(sampleRank, instanceOf(Rank.class));
    }

    @Test
    public void singlePlaceTitleString() {
        assertThat(
                sampleRank.getRankTitle(),
                is(String.valueOf(sampleRankId)));
    }

}
