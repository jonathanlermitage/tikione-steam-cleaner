package fr.tikione.ini.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * This class provides a wrapper for <code>java.io.InputStream</code> reader, with line reading support. Therefore, this wrapper is able to
 * detect the doubtless new line characters: Carriage Return and/or Line Feed.
 */
public class InputstreamLineReader extends AbstractLineReader {

    /** Wrapped input stream. */
    private final InputStream inputStream;

    /** Wrapped input stream reader. */
    private final InputStreamReader reader;

    /**
     * Create an <code>InputstreamLineReader</code> so that it uses <code>InputStream</code> as its input stream.
     *
     * @param inputStream input stream.
     * @param encoding file encoding.
     * @throws UnsupportedEncodingException if file encoding is not supported.
     */
    public InputstreamLineReader(InputStream inputStream, String encoding)
            throws UnsupportedEncodingException {
        super();
        this.inputStream = inputStream;
        reader = new InputStreamReader(inputStream, encoding);
    }

    @Override
    public void close()
            throws IOException {
        reader.close();
        inputStream.close();
    }

    @Override
    protected int read()
            throws IOException {
        return reader.read();
    }
}
