package contest;

import contest.winnerPlaces.PlaceAssigner;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
@XmlRootElement(name = "competition")
public class Competition {
    private final Collection<CompetitionEntry> entries;
    private final PlaceAssigner placeAssigner;

    public Competition(Collection<CompetitionEntry> entries, PlaceAssigner placeAssigner) {
        this.entries = entries;
        this.placeAssigner = placeAssigner;
    }

    Stream<CompetitionResult> getResults() {
        return entries.stream()
                .map(CompetitionResult::new);
    }

    @XmlElement(name = "results")
    public Stream<RankedCompetitionResult> getRankedResults() {
        return placeAssigner.assignPlaces(getResults());
    }

}
