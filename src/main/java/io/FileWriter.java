package io;

import java.io.OutputStreamWriter;
import java.util.stream.Stream;

/**
 * @author Martynas Maciulevičius.
 * @version 1.0 2015-09-29
 */
public interface FileWriter<T> {
    void write(OutputStreamWriter file, T input);

//    Stream<T> read(File file) throws FileNotFoundException;
}
