package contest.winnerPlaces.structures;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents place number or place range
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
@XmlRootElement(name = "rank")
@XmlType(propOrder = {"rankTitle"})
public class RankRange extends Rank {

    public RankRange(int from, int to) {
        super(String.valueOf(from + " - " + to));
    }
}
