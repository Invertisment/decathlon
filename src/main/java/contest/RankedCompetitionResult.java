package contest;

import com.sun.istack.internal.NotNull;
import contest.competitor.Competitor;
import contest.winnerPlaces.structures.Rank;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Contains a single entry of a Competitor
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
@XmlRootElement(name = "result")
@XmlType(propOrder = {"rank", "competitionEntry"})
public class RankedCompetitionResult extends CompetitionResult {
    private final Rank rank;

    public RankedCompetitionResult(@NotNull CompetitionEntry competitor, Rank rank) {
        super(competitor);
        if (rank == null) throw new NullPointerException("Place cannot be null");
        this.rank = rank;
    }

    public RankedCompetitionResult(CompetitionResult result, Rank rank) {
        this(result.getCompetitionEntry(), rank);
    }

    public Rank getRank() {
        return rank;
    }
}
