package contest.events;

import com.sun.istack.internal.NotNull;
import contest.events.formulas.EventFormula;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Describes single event
 *
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-25
 */
@XmlRootElement(name = "event")
@XmlType(propOrder = {"title"})
public class EventImpl implements Event {
    private final String title;
    private final EventFormula formula;

    public EventImpl(@NotNull String title, @NotNull EventFormula formula) {
        if (title == null || formula == null)
            throw new NullPointerException("Title or formula cannot be null");
        this.title = title;
        this.formula = formula;
    }

    public String getTitle() {
        return title;
    }

    public EventFormula getFormula() {
        return formula;
    }

}
