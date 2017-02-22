package logging;

import java.io.PrintStream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public class LoggerImpl implements Logger {
    private final PrintStream printStream;

    public LoggerImpl(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void logError(String info) {
        printStream.append(info);
    }
}
