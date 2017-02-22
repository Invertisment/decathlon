package contest.winnerPlaces.structures;

/**
 * Represents place number or place range
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
public class Rank {
    private final String rankTitle;

    public Rank(String rankTitle) {
        this.rankTitle = rankTitle;
    }

    public String getRankTitle() {
        return rankTitle;
    }
}
