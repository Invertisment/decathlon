package contest.competitor;

import com.sun.istack.internal.NotNull;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
public interface Competitor extends Comparable<Competitor> {

    String getName();

    int compareTo(@NotNull Competitor o);
}
