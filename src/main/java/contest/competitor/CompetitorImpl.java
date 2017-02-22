package contest.competitor;

import com.sun.istack.internal.NotNull;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
@XmlRootElement(name = "competitor")
@XmlType(propOrder = {"name"})
public class CompetitorImpl implements Competitor {

    private final String name;

    public CompetitorImpl(@NotNull String name) {
        if (name == null) throw new NullPointerException("Name cannot be null");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(@NotNull Competitor o) {
        return name.compareTo(o.getName());
    }
}
