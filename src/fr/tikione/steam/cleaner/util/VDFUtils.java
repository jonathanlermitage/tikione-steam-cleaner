package fr.tikione.steam.cleaner.util;

import fr.tikione.ini.util.StringHelper;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * Steam VDF files support tools.
 */
public class VDFUtils {

    /** Expected VDF file encoding (or compatible). */
    public static final String VDF_ENCODING = StandardCharsets.UTF_8.name();

    /** Line's marker that indicates a game installation reference. */
    private static final String TAG_INSTALLDIR = "\"INSTALLDIR\"";

    /** Line's marker that indicates a Steam reserved reference. */
    private static final String TAG_WINUI = "\\\\WINUI\"";

    /** Lenght of the {@link #TAG_INSTALLDIR} marker. */
    private static final int TAG_INSTALLDIR_LEN = TAG_INSTALLDIR.length();

    /** Suppresses default constructor, ensuring non-instantiability. */
    private VDFUtils() {
    }

    /**
     * Parses a VDF file to find registered Steam games.
     * @param vdf the VDF file to parse.
     * @return a list of games instalation directories.
     * @throws IOException if an error occurs during VDF file parsing.
     */
    public static List<File> getGames(String vdf)
            throws IOException {
        List<File> games = new ArrayList<>(8);
        LineIterator lineIterator = FileUtils.lineIterator(new File(vdf), VDF_ENCODING);
        String line;
        while (lineIterator.hasNext()) {
            line = lineIterator.nextLine();
            if (!StringHelper.strIsEmpty(line)) {
                line = StringHelper.tTrim(line);
                if (line.toUpperCase().startsWith(TAG_INSTALLDIR) && !line.toUpperCase().contains(TAG_WINUI)) {
                    line = StringHelper.tTrim(line.substring(TAG_INSTALLDIR_LEN));
                    if (line.startsWith("\"") && line.endsWith("\"")) {
                        line = line.substring(1, line.length() - 1).replaceAll(
                                Matcher.quoteReplacement("\\\\"),
                                Matcher.quoteReplacement("\\")) + File.separatorChar;
                        File gameFolder = new File(line);
                        if (gameFolder.exists()) { // FIXED check directory existence to avoid IOE logs from FileUtils.
                            games.add(gameFolder);
                        }
                    }
                }
            }
        }
        return games;
    }
}
