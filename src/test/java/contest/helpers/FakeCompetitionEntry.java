package contest.helpers;

import com.sun.istack.internal.NotNull;
import contest.CompetitionEntry;
import contest.competitor.Competitor;
import contest.competitor.Performance;
import contest.events.Event;

import java.util.Collections;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public class FakeCompetitionEntry extends CompetitionEntry {
    public FakeCompetitionEntry(int score, @NotNull Competitor competitor) {
        super(competitor, Collections.singletonList(new Performance() {

            @Override
            public double getPoints() {
                return 0;
            }

            @Override
            public Event getEvent() {
                return null;
            }

            @Override
            public int countScore() {
                return score;
            }
        }));
    }
}
