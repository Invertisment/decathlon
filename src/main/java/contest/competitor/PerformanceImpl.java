package contest.competitor;

import com.sun.istack.internal.NotNull;
import contest.events.Event;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Combines Event and Contestant's points
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-27
 */
@XmlRootElement(name = "performance")
@XmlType(propOrder = {"points", "event"})
public class PerformanceImpl implements Performance {

    private final double points;
    private final Event event;

    public PerformanceImpl(double points, @NotNull Event event) {
        if (event == null) throw new NullPointerException("null Event is not allowed");
        this.points = points;
        this.event = event;
    }

    public double getPoints() {
        return points;
    }

    public Event getEvent() {
        return event;
    }

    public int countScore() {
        return event.getFormula().countScore(points);
    }
}
