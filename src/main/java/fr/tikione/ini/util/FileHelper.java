package fr.tikione.ini.util;

/**
 * This class offers many usual file operations.
 */
public class FileHelper {

    /** Suppresses default constructor, ensuring non-instantiability. */
    private FileHelper() {
    }

    /**
     * Get platform's default new line character(s), usually CR, LF, or CR+LF.
     *
     * @return new line character(s).
     */
    public static String getPlatformNewLine() {
        return System.getProperty("line.separator");
    }

    /**
     * Get platform's default file encoding.
     *
     * @return encoding.
     */
    public static String getPlatformEncoding() {
        return System.getProperty("file.encoding");
    }
}
