package logging;

import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public class LoggerImplTests {
    @Test
    public void usesGivenStream() {
        String err = "213";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        LoggerImpl logger = new LoggerImpl(ps);
        logger.logError(err);
        String content = baos.toString();
        Assert.assertEquals(content, err);
    }
}
