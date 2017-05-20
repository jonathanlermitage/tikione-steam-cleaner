package fr.tikione.ini.util;

import java.io.CharConversionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class offers many usual string operations.
 */
public class StringHelper {

    /** The white-space character (<code>'&#92;u0020'</code>). */
    public static final char SPACE = '\u0020';

    /** The tabulation character (<code>'&#92;u0009'</code>). */
    public static final char TAB = '\u0009';

    /** The Carriage Return character (CR). */
    public static final char CARRIAGE_RETURN = '\r';

    /** The Line Feed character (LF). */
    public static final char LINE_FEED = '\n';

    /** Hexadecimal digits. */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /** Suppresses default constructor, ensuring non-instantiability. */
    private StringHelper() {
    }

    /**
     * Check a regular expression.
     *
     * @param src the text to examine.
     * @param pattern the compiled regular expression, targeted area are enclosed with parenthesis.
     * @return <code>true</code> if the regular expression is checked, otherwise <code>false</code>.
     */
    public static boolean checkRegex(String src, Pattern pattern) {
        return pattern.matcher(src).find();
    }

    /**
     * Return <em>N</em> concatenations of string.
     * <p>Example: <code>StringHelper.generateStr("AB", 4)</code> will return "ABABABAB".
     *
     * @param model the string to concatenate.
     * @param occurences the number of concatenations. If zero of negative, the method will return an empty string.
     * @return the concatenations result.
     */
    public static String generateStrConcat(String model, int occurences) {
        String resConcat;
        if (occurences > 0) {
            StringBuilder buffer = new StringBuilder(model.length() * occurences);
            for (int numOcc = 0; numOcc < occurences; numOcc++) {
                buffer.append(model);
            }
            resConcat = buffer.toString();
        } else {
            resConcat = "";
        }
        return resConcat;
    }

    /**
     * Return the capturing groups from the regular expression in the string.
     *
     * @param src the string to search in.
     * @param pattern the compiled pattern.
     * @param expectedNbMatchs the expected tokens matched, for performance purpose.
     * @return all the strings matched (in an ArrayList).
     */
    public static List<String> getGroupsFromRegex(String src, Pattern pattern, int expectedNbMatchs) {
        List<String> res = new ArrayList<>(expectedNbMatchs);
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) {
            for (int group = 1; group <= matcher.groupCount(); group++) {
                res.add(matcher.group(group));
            }
        }
        return res;
    }

    /**
     * Find the "prefix" string the most at the left in the source string.<br />
     * Example: with "my name is" as the source string, and {"name", "is"} as the prefix strings, "name" is returned.
     *
     * @param str the source string.
     * @param prefixes the prefix strings array.
     * @return the prefix that is the most at the left. If no prefix is found, <code>null</code> is returned.
     */
    public static String mostLeft(String str, String[] prefixes) {
        String prefixFound = null;
        int bestPrefixPos = str.length();
        for (String pre : prefixes) {
            int pos = str.indexOf(pre);
            if (pos != -1 && pos < bestPrefixPos) {
                bestPrefixPos = pos;
                prefixFound = pre;
            }
        }
        return prefixFound;
    }

    /**
     * Find the "suffix" string the most at the right in the source string.<br />
     * Example: with "my name is" as the source string, and {"name", "is"} as the suffix strings array, "is" is returned.
     *
     * @param str the source string.
     * @param suffixes the suffix strings array.
     * @return the suffix that is the most at the right. If no suffix is found, <code>null</code> is returned.
     */
    public static String mostRight(String str, String[] suffixes) {
        String suffixFound = null;
        int bestSuffixPos = -1;
        for (String pre : suffixes) {
            int pos = str.lastIndexOf(pre);
            if (pos != -1 && pos > bestSuffixPos) {
                bestSuffixPos = pos;
                suffixFound = pre;
            }
        }
        return suffixFound;
    }

    /**
     * Replace the first substring of this string that matches the given compiled pattern with the literalized given replacement.
     *
     * @param src the character sequence to be matched.
     * @param pattern the compiled regular expression to which this string is to be matched.
     * @param replacement the replacement string (internally literalized).
     * @return the resulting string.
     */
    public static String replaceFirst(String src, Pattern pattern, String replacement) {
        return pattern.matcher(src).replaceFirst(Matcher.quoteReplacement(replacement));
    }

    /**
     * Splits this string around matches of the given regular expression. All computed results have their leading and trailing white-space
     * characters (<code>'&#92;u0020'</code>) and tabulations (<code>'&#92;u0009'</code>) removed.
     *
     * @param str the string to split.
     * @param regex the delimiting regular expression.
     * @return the array of strings computed by splitting this string around matches of the given regular expression.
     */
    public static String[] tSplit(String str, String regex) {
        String[] res = str.split(Matcher.quoteReplacement(regex), -1);
        for (int nElt = 0; nElt < res.length; nElt++) {
            res[nElt] = tTrim(res[nElt]);
        }
        return res;
    }

    /**
     * Indicate if all the strings are null or length equals to zero or are entirely packed with spaces and/or tabulations.
     *
     * @param strs the strings to check.
     * @return <code>true</code> if all the strings are empty, otherwise <code>false</code>.
     */
    public static boolean strAreEmpty(String... strs) {
        boolean res = true;
        for (String s : strs) {
            if (strIsNotEmpty(s)) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * Indicate if the string is null or length equals to zero or is entirely packed with spaces and/or tabulations.
     *
     * @param str the string to check.
     * @return <code>true</code> if the string is empty, otherwise <code>false</code>.
     */
    public static boolean strIsEmpty(String str) {
        return (null == str) || (0 == tTrim(str).length());
    }

    /**
     * Indicate if the string is not null and length greater than zero and is not entirely packed with spaces and/or tabulations.
     *
     * @param str the string to check.
     * @return <code>true</code> if the string is not empty, otherwise <code>false</code>.
     */
    public static boolean strIsNotEmpty(String str) {
        return (null != str) && (0 < tTrim(str).length());
    }

    /**
     * Indicate if the string starts with one of the specified prefixes.
     *
     * @param str the source string.
     * @param prefixes the list of prefixes.
     * @return <code>true</code> if the source string starts with one of these prefixes, otherwise <code>false</code>.
     */
    public static boolean strStartsWith(String str, String... prefixes) {
        boolean res = false;
        for (String s : prefixes) {
            if (str.startsWith(s)) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * Indicate if the string ends with one of the specified prefixes.
     *
     * @param str the source string.
     * @param suffixes the list of prefixes.
     * @return <code>true</code> if the source string ends with one of these prefixes, otherwise <code>false</code>.
     */
    public static boolean strEndsWith(String str, String... suffixes) {
        boolean res = false;
        for (String s : suffixes) {
            if (str.endsWith(s)) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * Return a copy of the string, with leading and trailing {@link #SPACE} and {@link #TAB} omitted.
     *
     * @param str the string.
     * @return A copy of this string with leading and trailing white-space/tab removed, or this string if it has no leading or trailing
     *         white-space/tab.
     */
    public static String tTrim(String str) {
        return tTrim(str, false);
    }

    /**
     * Return a copy of the string, with leading and trailing {@link #SPACE} and {@link #TAB} omitted.
     *
     * @param str the string.
     * @param removeCRLF remove leading and trailing ({@link #CARRIAGE_RETURN} and {@link #LINE_FEED}).
     * @return A copy of this string with leading and trailing white-space/tab removed, or this string if it has no leading or trailing
     *         white-space/tab.
     */
    public static String tTrim(String str, boolean removeCRLF) {
        int strlen = str.length();
        int endpos = strlen;
        int startpos = 0;
        char[] val = new char[strlen];
        str.getChars(0, strlen, val, 0);
        if (removeCRLF) {
            while ((startpos < strlen) && ((val[startpos] == SPACE) || (val[startpos] == TAB)
                    || (val[startpos] == CARRIAGE_RETURN) || (val[startpos] == LINE_FEED))) {
                startpos++;
            }
            while ((endpos > startpos) && ((val[endpos - 1] == SPACE) || (val[endpos - 1] == TAB)
                    || (val[startpos - 1] == CARRIAGE_RETURN) || (val[startpos - 1] == LINE_FEED))) {
                endpos--;
            }
        } else {
            while ((startpos < strlen) && ((val[startpos] == SPACE) || (val[startpos] == TAB))) {
                startpos++;
            }
            while ((endpos > startpos) && ((val[endpos - 1] == SPACE) || (val[endpos - 1] == TAB))) {
                endpos--;
            }
        }
        return ((startpos > 0) || (endpos < strlen)) ? str.substring(startpos, endpos) : str;
    }

    /**
     * Return a copy of the string, with leading (on the left side) {@link #SPACE}, {@link #TAB} omitted.
     *
     * @param str the string.
     * @return A copy of this string with leading white-space/tab removed, or this string if it has no leading white-space/tab.
     */
    public static String tTrimLead(String str) {
        return tTrimLead(str, false);
    }

    /**
     * Return a copy of the string, with leading (on the left side) {@link #SPACE}, {@link #TAB} omitted, and optionally
     * {@link #CARRIAGE_RETURN} and {@link #LINE_FEED} too.
     *
     * @param str the string.
     * @param removeCRLF remove leading ({@link #CARRIAGE_RETURN} and {@link #LINE_FEED}).
     * @return A copy of this string with leading white-space/tab removed, or this string if it has no leading white-space/tab.
     */
    public static String tTrimLead(String str, boolean removeCRLF) {
        int strlen = str.length();
        int startpos = 0;
        char[] val = new char[strlen];
        str.getChars(0, strlen, val, 0);
        if (removeCRLF) {
            while ((startpos < strlen) && ((val[startpos] == SPACE) || (val[startpos] == TAB)
                    || (val[startpos] == CARRIAGE_RETURN) || (val[startpos] == LINE_FEED))) {
                startpos++;
            }
        } else {
            while ((startpos < strlen) && ((val[startpos] == SPACE) || (val[startpos] == TAB))) {
                startpos++;
            }
        }
        return (startpos > 0) ? str.substring(startpos, strlen) : str;
    }

    /**
     * Return a copy of the string, with trailing (on the right side) {@link #SPACE}, {@link #TAB} omitted.
     *
     * @param str the string.
     * @return A copy of this string with trailing white-space/tab removed, or this string if it has no trailing white-space/tab.
     */
    public static String tTrimTrail(String str) {
        return tTrimTrail(str, false);
    }

    /**
     * Return a copy of the string, with trailing (on the right side) {@link #SPACE}, {@link #TAB} omitted, and optionally
     * {@link #CARRIAGE_RETURN} and {@link #LINE_FEED} too.
     *
     * @param str the string.
     * @param removeCRLF remove trailing New Lines ({@link #CARRIAGE_RETURN} and {@link #LINE_FEED}).
     * @return A copy of this string with trailing white-space/tab removed, or this string if it has no trailing white-space/tab.
     */
    public static String tTrimTrail(String str, boolean removeCRLF) {
        int strlen = str.length();
        int endpos = strlen;
        char[] val = new char[strlen];
        str.getChars(0, strlen, val, 0);
        if (removeCRLF) {
            while ((endpos > 0) && ((val[endpos - 1] == SPACE) || (val[endpos - 1] == TAB)
                    || (val[endpos - 1] == CARRIAGE_RETURN) || (val[endpos - 1] == LINE_FEED))) {
                endpos--;
            }
        } else {
            while ((endpos > 0) && ((val[endpos - 1] == SPACE) || (val[endpos - 1] == TAB))) {
                endpos--;
            }
        }
        return (endpos < strlen) ? str.substring(0, endpos) : str;
    }

    /**
     * Return a concatenation of all values of a <code>Collection</code>, with a specified separator between all of them.
     * <p>Example: with Collection of three elements "AB", "C" and "D", and a "--" separator, this method will return "AB--C--D". With an
     * empty separator, the result would be "ABCD".
     *
     * @param collection the <code>Collection</code> to decompose.
     * @param separatorBetween the separator. It can be an empty string.
     * @return the <code>Collection</code> decomposition.
     */
    public static String decomposeCollection(Collection<String> collection, String separatorBetween) {
        return decomposeCollection(collection, separatorBetween, (collection.size() * separatorBetween.length() * 16) + 32);
    }

    /**
     * Return a concatenation of all values of a <code>Collection</code>, with a specified separator between all of them.
     * <p>Example: with Collection of three elements "AB", "C" and "D", and a "--" separator, this method will return "AB--C--D". With an
     * empty separator, the result would be "ABCD".
     *
     * @param collection the <code>Collection</code> to decompose.
     * @param separatorBetween the separator. It can be an empty string.
     * @param expectedResSize the expected size of decomposition. For performance purpose only.
     * @return the <code>Collection</code> decomposition.
     */
    public static String decomposeCollection(Collection<String> collection, String separatorBetween, int expectedResSize) {
        String decomposition;
        if (collection.isEmpty()) {
            decomposition = "";
        } else {
            StringBuilder buffer = new StringBuilder(expectedResSize);
            for (String line : collection) {
                buffer.append(line).append(separatorBetween);
            }
            buffer.delete(buffer.length() - separatorBetween.length(), buffer.length());
            decomposition = buffer.toString();
        }
        return decomposition;
    }

    /**
     * States used by {@link StringHelper#convertUnicodeEscapesToUTF8(java.lang.String)}. For internal use only.
     */
    private static enum UnicodeConvertParseState {

        NORMAL,
        ESCAPE,
        UNICODE_ESCAPE

    }

    /**
     * Convert Unicode escapes to UTF-8 characters.
     * Mainly based on Dr Xi code (<a href="http://www.xinotes.org/notes/note/813/">http://www.xinotes.org/notes/note/813/</a>).
     *
     * @param unicodeString the string to convert Unicode escapes.
     * @return the String where Unicode escapes have been converted to UTF-8 characters.
     * @throws CharConversionException if an invalid Unicode escape is detected.
     */
    @SuppressWarnings("ValueOfIncrementOrDecrementUsed")
    public static String convertUnicodeEscapesToUTF8(String unicodeString)
            throws CharConversionException {
        String res;
        if (strIsEmpty(unicodeString)) { // Test added: avoid NullPointerException on empty strings.
            res = unicodeString;
        } else {
            char[] out = new char[unicodeString.length()];
            UnicodeConvertParseState state = UnicodeConvertParseState.NORMAL;
            int normalCounter = 0, uniCounter = 0, unicode = 0;
            char currChar = ' ';
            int uniStrLen = unicodeString.length();
            for (int offset = 0; offset < uniStrLen; offset++) {
                currChar = unicodeString.charAt(offset);
                if (state == UnicodeConvertParseState.ESCAPE) {
                    if (currChar == 'u') {
                        state = UnicodeConvertParseState.UNICODE_ESCAPE;
                        unicode = 0;
                    } else { // We don't care about other escapes.
                        out[normalCounter++] = '\\';
                        out[normalCounter++] = currChar;
                        state = UnicodeConvertParseState.NORMAL;
                    }
                } else if (state == UnicodeConvertParseState.UNICODE_ESCAPE) {
                    if ((currChar >= '0') && (currChar <= '9')) {
                        unicode = (unicode << 4) + currChar - '0';
                    } else if ((currChar >= 'a') && (currChar <= 'f')) {
                        unicode = (unicode << 4) + 10 + currChar - 'a';
                    } else if ((currChar >= 'A') && (currChar <= 'F')) {
                        unicode = (unicode << 4) + 10 + currChar - 'A';
                    } else {
                        throw new CharConversionException("Malformed Unicode escape");
                    }
                    uniCounter++;
                    if (uniCounter == 4) {
                        out[normalCounter++] = (char) unicode;
                        uniCounter = 0;
                        state = UnicodeConvertParseState.NORMAL;
                    }
                } else if (currChar == '\\') {
                    state = UnicodeConvertParseState.ESCAPE;
                } else {
                    out[normalCounter++] = currChar;
                }
            }
            if (state == UnicodeConvertParseState.ESCAPE) {
                out[normalCounter++] = currChar;
            }
            res = new String(out, 0, normalCounter);
        }
        return res;
    }

    /**
     * Convert UTF-8 characters to Unicode escapes.
     * Mainly based on Dr Xi code (<a href="http://www.xinotes.org/notes/note/812/">http://www.xinotes.org/notes/note/812/</a>).
     *
     * @param utf8String the string to convert UTF-8 characters.
     * @return the String where UTF-8 characters have been converted to Unicode escapes.
     */
    public static String convertUTF8ToUnicodeEscapes(String utf8String) {
        String res;
        if (strIsEmpty(utf8String)) { // Test added: avoid NullPointerException on empty strings.
            res = utf8String;
        } else {
            StringBuilder resBuffer = new StringBuilder((int) (utf8String.length() * 1.25));
            for (int offset = 0; offset < utf8String.length(); offset++) {
                char currChar = utf8String.charAt(offset);
                if ((currChar >> 7) > 0) {
                    resBuffer.append("\\u");
                    resBuffer.append(HEX_CHAR[(currChar >> 12) & 0xF]); // Append the hex character for the left-most 4-bits.
                    resBuffer.append(HEX_CHAR[(currChar >> 8) & 0xF]);  // Hex for the second group of 4-bits from the left.
                    resBuffer.append(HEX_CHAR[(currChar >> 4) & 0xF]);  // Hex for the third group.
                    resBuffer.append(HEX_CHAR[currChar & 0xF]);         // Hex for the last group, e.g., the right most 4-bits.
                } else {
                    resBuffer.append(currChar);
                }
            }
            res = resBuffer.toString();
        }
        return res;
    }
}
