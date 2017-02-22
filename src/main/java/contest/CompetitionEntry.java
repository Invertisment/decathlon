package contest;

import com.sun.istack.internal.NotNull;
import contest.competitor.Competitor;
import contest.competitor.Performance;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
@XmlRootElement(name = "entry")
@XmlType(propOrder = {"competitor", "performance"})
public class CompetitionEntry {
    private final Competitor competitor;
    private final Collection<Performance> performance;

    public CompetitionEntry(@NotNull Competitor competitor, @NotNull Collection<Performance> performance) {
        if (competitor == null || performance == null)
            throw new NullPointerException("null values of Competitor or Performance are not allowed");
        this.competitor = competitor;
        this.performance = performance;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public Collection<Performance> getPerformance() {
        return performance;
    }

    public int getScore() {
        return performance.stream()
                .mapToInt(Performance::countScore)
                .sum();
    }
}
