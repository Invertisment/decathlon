package contest;

import com.sun.istack.internal.NotNull;

/**
 * Contains a single entry of a Competitor
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
public class CompetitionResult implements Comparable<CompetitionResult> {
    private final CompetitionEntry competitionEntry;

    public CompetitionResult(@NotNull CompetitionEntry competitionEntry) {
        if (competitionEntry == null) throw new NullPointerException("CompetitionEntry cannot be null");
        this.competitionEntry = competitionEntry;
    }

    public int getScore() {
        return competitionEntry.getScore();
    }

    public CompetitionEntry getCompetitionEntry() {
        return competitionEntry;
    }

    @Override
    public int compareTo(CompetitionResult o) {
        int scoreComparison = Integer.compare(o.getScore(), getScore());
        if (scoreComparison == 0) {
            return competitionEntry.getCompetitor().compareTo(o.competitionEntry.getCompetitor());
        } else return scoreComparison;
    }
}
