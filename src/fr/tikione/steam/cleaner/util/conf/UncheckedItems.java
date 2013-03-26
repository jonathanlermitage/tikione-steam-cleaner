package fr.tikione.steam.cleaner.util.conf;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.ini.Ini;
import fr.tikione.steam.cleaner.Main;
import java.io.CharConversionException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

/**
 * List of unchecked items in the redistributable packages list.
 */
public class UncheckedItems {

    /** INI configuration file section : unchecked items. */
    private static final String CONFIG_UNCHECKED_REDIST_ITEMS = "UNCHECKED_REDIST_ITEMS";

    /** INI configuration file key : unchecked items. */
    private static final String CONFIG_UNCHECKED_REDIST_ITEMS__ITEM_LIST = "itemList";

    /** File to use for configuration loading and saving. */
    private final File configFile;

    /** Configuration object. */
    private final Ini ini;

    private boolean updated = false;

    /**
     * Load application configuration file. A default configuration file is created if necessary.
     *
     * @throws IOException if an I/O error occurs while retrieving the internal default configuration file, or while writing this default
     *         configuration file.
     */
    public UncheckedItems()
            throws IOException {
        File backupConfigFile = new File("conf/backup/tikione-steam-cleaner_unchecked-items.ini");
        File userprofile = new File(System.getenv("USERPROFILE") + "/.tikione/");
        userprofile.mkdirs();
        configFile = new File(userprofile.getAbsolutePath() + "/tikione-steam-cleaner_unchecked-items.ini");
        if (!configFile.exists()) {
            org.apache.commons.io.FileUtils.copyFile(backupConfigFile, configFile);
        }
        ini = new Ini();
        ini.getConfig().enableParseLineConcat(false);
        ini.getConfig().enableReadUnicodeEscConv(false);
        ini.load(configFile, Main.CONF_ENCODING);
    }

    /**
     * Saves the unchecked items list into a file.
     *
     * @throws IOException if an I/O error occurs while writing the file.
     */
    public void save()
            throws IOException {
        if (updated || !configFile.exists()) {
            ini.store(configFile, Main.CONF_ENCODING, Main.CONF_NEWLINE);
        }
    }

    /**
     * Get the list of unchecked items memorized from the file.
     *
     * @return the list of unchecked items.
     * @throws CharConversionException if an error occurs while retrieving the list from the file (invalid characters).
     * @throws InfinitiveLoopException if an error occurs while retrieving the list from the file (file parsing error).
     */
    @SuppressWarnings("unchecked")
    public List<String> getUncheckedItems()
            throws CharConversionException,
                   InfinitiveLoopException {
        String itemTable = ini.getKeyValue(null, CONFIG_UNCHECKED_REDIST_ITEMS, CONFIG_UNCHECKED_REDIST_ITEMS__ITEM_LIST);
        List<String> res;
        if (itemTable == null) {
            res = (List<String>) Collections.EMPTY_LIST;
        } else {
            res = Arrays.asList(itemTable.split(Matcher.quoteReplacement("\""), 0));
        }
        return res;
    }

    /**
     * Set the list of unchecked items to memorize.
     *
     * @param items the items to memorize.
     * @throws CharConversionException
     * @throws InfinitiveLoopException
     */
    public void setUncheckedItems(List<String> items)
            throws CharConversionException,
                   InfinitiveLoopException {
        List<String> prevItems = getUncheckedItems();
        if (!prevItems.containsAll(items) || !items.containsAll(prevItems)) {
            updated = true;
            StringBuilder buffer = new StringBuilder(512);
            boolean first = true;
            for (String item : items) {
                if (first) {
                    buffer.append(item);
                } else {
                    buffer.append('\"').append(item);
                }
                first = false;
            }
            ini.setKeyValue(CONFIG_UNCHECKED_REDIST_ITEMS, CONFIG_UNCHECKED_REDIST_ITEMS__ITEM_LIST, buffer.toString());
        }
    }
}
