package fr.tikione.steam.cleaner.util.conf;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.ini.Ini;
import fr.tikione.steam.cleaner.Main;
import fr.tikione.steam.cleaner.util.Log;
import java.io.CharConversionException;
import java.io.File;
import java.io.IOException;

/**
 * I18n language files encoding handler.
 */
public class I18nEncoding {

    /** Singleton handler. */
    private static final I18nEncoding i18nEncoding;

    /** File to use for configuration loading and saving. */
    private final File configFile;

    /** Configuration object. */
    private final Ini ini;

    static {
        // Singleton creation.
        try {
            i18nEncoding = new I18nEncoding();
        } catch (IOException ex) {
            Log.error(ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get the configuration handler as a singleton.
     *
     * @return the configuration handler singleton.
     */
    public static synchronized I18nEncoding getInstance() {
        return i18nEncoding;
    }

    /**
     * Load application configuration file.
     *
     * @throws IOException if an I/O error occurs while retrieving the internal default configuration file.
     */
    private I18nEncoding()
            throws IOException {
        configFile = new File("conf/i18n/encoding.ini");
        ini = new Ini();
        ini.getConfig().enableParseLineConcat(true);
        ini.getConfig().enableReadUnicodeEscConv(true);
        ini.load(configFile, Main.CONF_ENCODING);
    }

    public String getLngEncoding(String locale)
            throws CharConversionException,
                   InfinitiveLoopException {
        return ini.getKeyValue(Main.CONF_ENCODING, "", locale);
    }
}
