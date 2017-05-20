package fr.tikione.ini.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * This class provides a wrapper for readers, with line reading support. Therefore, this wrapper is able to detect the doubtless new line
 * character(s): Carriage Return and/or Line Feed.
 */
public abstract class AbstractLineReader implements Closeable {

    /** Initial average line length (in bytes). */
    private static final int AVG_LINE_LEN = 512;

    private final StringBuilder buffer = new StringBuilder(AVG_LINE_LEN);

    /** Carriage Return characters counter. */
    private int nbCR = 0;

    /** Line Feed characters counter. */
    private int nbLF = 0;

    /** The first character read after the latest new line. */
    private char nextLineFirstChar;

    /** Indicate if the first character after the latest new line is already read. */
    private boolean isFirstCharRead = false;

    /** Indicate a sequence of two consecutive carriage return characters. */
    private boolean secondCR = false;

    /**
     * Close the input stream given with the constructor.
     *
     * @throws IOException if an I/O error occurs while closing the input stream.
     */
    @Override
    public abstract void close()
            throws IOException;

    /**
     * Return the new line characters combination CR (Carriage Return), LF (Line Feed) or CRLF according to the <code>InputStream</code> 
     * object given to the current <code>AbstractLineReader</code> object. It is based on the number of apparition of each new line
     * character.<br />
     * If CR and LF have appears the same number of times, the result is CRLF, otherwise the most used character is returned.<br />
     * This method needs the {@link #readLine()} method to be called enough times in order to calculate the right number of each new
     * line characters apparition.<br/>
     * If this method has not been called, the result is the platform's new line characters.
     *
     * @return the new line character(s) according to the current <code>LineReader</code> object.
     */
    public String getNewLineStr() {
        String newLineStr;
        if (0 == nbCR && 0 == nbLF) {
            newLineStr = FileHelper.getPlatformNewLine();
        } else if (nbCR == nbLF) {
            newLineStr = "\r\n";
        } else if (nbCR > nbLF) {
            newLineStr = "\r";
        } else {
            newLineStr = "\n";
        }
        return newLineStr;
    }

    /**
     * Read a single character.
     *
     * @return The character read, as an integer in the range 0 to 65535 (<code>0x00-0xffff</code>), or -1 if the end of the stream has
     *         been reached.
     * @throws IOException If an I/O error occurs.
     */
    protected abstract int read()
            throws IOException;

    /**
     * Read a line of text.
     *
     * @return A String containing the contents of the line, not including any line-termination characters, or null if the end of the
     *         stream has been reached.
     * @throws IOException if an I/O error occurs.
     */
    @SuppressWarnings("NestedAssignment")
    public String readLine()
            throws IOException {
        int rchar;
        boolean charRead = false;
        boolean readLine = false;
        if (secondCR) {
            charRead = true;
            secondCR = false;
        } else {
            if (isFirstCharRead) {
                buffer.append(nextLineFirstChar);
                isFirstCharRead = false;
            }
            while (!readLine && (rchar = read()) != -1) {
                char crchar = (char) rchar;
                charRead = true;
                if ((char) rchar == StringHelper.LINE_FEED) {
                    nbLF++;
                    readLine = true;
                } else if (crchar == StringHelper.CARRIAGE_RETURN) {
                    nbCR++;
                    readLine = true;
                    int nextChar = read();
                    char cnextChar = (char) nextChar;
                    if (StringHelper.LINE_FEED == cnextChar) {
                        nbLF++;
                        isFirstCharRead = false;
                    } else {
                        nextLineFirstChar = cnextChar;
                        isFirstCharRead = true;
                    }
                } else {
                    buffer.append(crchar);
                }
            }
        }
        String resLine = charRead || isFirstCharRead ? buffer.toString() : null;
        buffer.delete(0, buffer.length());
        return resLine;
    }
}
