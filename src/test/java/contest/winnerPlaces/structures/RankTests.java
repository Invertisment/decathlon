package contest.winnerPlaces.structures;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public class RankTests {

    @Test
    public void itShouldSaveAString() {
        String sampleString = "Le string";
        Rank rank = new Rank(sampleString) {
        };
        assertThat(
                rank.getRankTitle(),
                is(sampleString));
    }
}
