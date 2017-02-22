package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public interface FileReader<T> {
    Stream<T> read(InputStreamReader file);

    Stream<T> read(File file) throws FileNotFoundException;
}
