package io;

import contest.events.Event;
import logging.Logger;
import logging.LoggerImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

import static junit.framework.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public class FileReaderTests {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void hasLogger() {
        ByteArrayOutputStream byteArrStream = new ByteArrayOutputStream();
        PrintStream errStream = new PrintStream(byteArrStream);
        Optional<Logger> logger = Optional.of(new LoggerImpl(errStream));

        Optional<Logger> maybeLogger = new FileReaderImpl(logger) {
            @Override
            public Stream<Event> read(InputStreamReader file) {
                return null;
            }

            public Optional<Logger> getLogger() {
                return logger;
            }
        }.getLogger();
        assertTrue(maybeLogger.isPresent());
        assertThat(maybeLogger.get(), is(logger.get()));
    }

    @Test
    public void opensFile() throws IOException {
        File input = temporaryFolder.newFile("file.txt");
        String fileContents = "abc";
        try (PrintWriter writer = new PrintWriter(input)) {
            writer.print(fileContents);
        }

        FileReaderImpl<String> fileReader = new FileReaderImpl<String>(Optional.empty()) {

            @Override
            public Stream<String> read(InputStreamReader file) {
                return new BufferedReader(file).lines();
            }
        };
        Iterator<String> strings = fileReader.read(input).iterator();

        assertEquals(
                strings.next(),
                fileContents);
        assertFalse(strings.hasNext());
    }

}
