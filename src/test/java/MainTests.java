import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-30
 */
public class MainTests {
    @Test
    public void parsesArgs() {
        Main main = new Main(Optional.empty());
        Optional<StringTuple> stringTuple = main.parseArgs(new String[]{"a", "b"});

        Assert.assertEquals(stringTuple.get().in, "a");
        Assert.assertEquals(stringTuple.get().out, "b");
    }

    @Test
    public void parsesBadArgs() {
        Main main = new Main(Optional.empty());
        Optional<StringTuple> stringTuple = main.parseArgs(new String[]{"a"});

        Assert.assertEquals(stringTuple.isPresent(), false);
    }

    @Test
    public void parsesNoArgs() {
        Main main = new Main(Optional.empty());
        Optional<StringTuple> stringTuple = main.parseArgs(new String[0]);

        Assert.assertEquals(stringTuple.isPresent(), false);
    }

    @Test
    public void parsesReduntantArgs() {
        Main main = new Main(Optional.empty());
        Optional<StringTuple> stringTuple = main.parseArgs(new String[3]);

        Assert.assertEquals(stringTuple.isPresent(), false);
    }
}
