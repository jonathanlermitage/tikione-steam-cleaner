package fr.tikione.ini.util;

/**
 * Utility class for INI parsing.
 */
public class IniHelper {

    /** Suppresses default constructor, ensuring non-instantiability. */
    private IniHelper() {
    }

    /**
     * Gets the key name and value in a key affectation line.
     *
     * @param line the INI line.
     * @param affectation characters used to separate keys from values.
     * @return a table of two strings: the first contains the key name, the second contains the key value. If it is an invalid
     *         affectation line (no affectation symbol), <code>null</code> is returned.
     */
    public static String[] extractKeyNameAndValueFromLine(String line, String[] affectation) {
        String[] res = null;
        String affectationFound = StringHelper.mostLeft(line, affectation);
        if (affectationFound != null) {
            int affectationPos = line.indexOf(affectationFound);
            String key = StringHelper.tTrim(line.substring(0, affectationPos));
            String value = StringHelper.tTrim(line.substring(affectationPos + affectationFound.length()));
            res = new String[]{key, value};
        }
        return res;
    }

    /**
     * Gets the section name from a section declaration line (typically in "[section]", "section" is returned).
     *
     * @param line the line.
     * @param sectionStart characters used to represent a section starting.
     * @param sectionEnd characters used to represent a section ending.
     * @return the section name. If it is an invalid section line (no section declaration), <code>null</code> is returned.
     */
    public static String extractSectionNameFromLine(String line, String[] sectionStart, String[] sectionEnd) {
        String res = null;
        String tline = StringHelper.tTrim(line);
        if (2 < tline.length() && StringHelper.strStartsWith(tline, sectionStart) && StringHelper.strEndsWith(tline, sectionEnd)) {
            String secStartFound = StringHelper.mostLeft(tline, sectionStart);
            String secEndFound = StringHelper.mostRight(tline, sectionEnd);
            int start = tline.indexOf(secStartFound);
            int end = tline.indexOf(secEndFound);
            res = StringHelper.tTrim(tline.substring(start + 1, end));
        }
        return res;
    }
}
