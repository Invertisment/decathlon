package io;

import logging.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public abstract class FileReaderImpl<T> implements FileReader<T> {
    protected final Optional<Logger> logger;

    public FileReaderImpl(Optional<Logger> logger) {
        this.logger = logger;
    }

    public Stream<T> read(File file) throws FileNotFoundException {
        return read(new InputStreamReader(new FileInputStream(file)));
    }
}
