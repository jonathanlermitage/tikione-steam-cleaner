package fr.tikione.steam.cleaner.util.conf;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.ini.Ini;
import fr.tikione.steam.cleaner.Main;
import fr.tikione.steam.cleaner.util.Log;
import java.io.CharConversionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * List of items patterns to exclude from the search path.
 */
public class DangerousItems {

    private static final DangerousItems dangerousItems;

    /** INI configuration file section : unchecked items. */
    private static final String CONFIG_AUTOEXCLUDE_PATTERNS = "AUTOEXCLUDE_PATTERNS";

    /** INI configuration file key : unchecked items. */
    private static final String CONFIG_AUTOEXCLUDE_PATTERNS__FOLDERS_LIST = "folderPatterns";

    /** File to use for configuration loading and saving. */
    private static File configFile;

    /** Configuration object. */
    private static Ini ini;

    static {
        // Singleton creation.
        try {
            dangerousItems = new DangerousItems();
        } catch (Exception ex) {
            Log.error(ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get the configuration handler as a singleton.
     *
     * @return the configuration handler singleton.
     */
    public static synchronized DangerousItems getInstance() {
        return dangerousItems;
    }

    /**
     * Load application configuration file.
     *
     * @throws IOException if an I/O error occurs while retrieving the internal default configuration file.
     */
    private DangerousItems()
            throws IOException {
        configFile = new File("conf/tikione-steam-cleaner_dangerous-items.ini");
        ini = new Ini();
        ini.getConfig().enableParseLineConcat(true);
        ini.getConfig().enableReadUnicodeEscConv(true);
        ini.load(configFile, Main.CONF_ENCODING);
    }

    public List<Pattern> getDangerousFolders()
            throws CharConversionException,
                   InfinitiveLoopException {
        List<Pattern> dangerousFolders = new ArrayList<>();
        String[] keys = ini.getKeyValue("", CONFIG_AUTOEXCLUDE_PATTERNS, CONFIG_AUTOEXCLUDE_PATTERNS__FOLDERS_LIST).split("\"", 0);
        for (String pattern : keys) {
            if (pattern.length() > 0) {
                try {
                    dangerousFolders.add(Pattern.compile(pattern));
                } catch (PatternSyntaxException ex) {
                    Log.error(ex);
                }
            }
        }
        return dangerousFolders;
    }
}
