package fr.tikione.steam.cleaner.util;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.ini.Ini;
import fr.tikione.steam.cleaner.Main;
import fr.tikione.steam.cleaner.util.conf.I18nEncoding;
import java.io.CharConversionException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * Translation handler.
 */
public class Translation {

    private static final String DEFAULT_LANGCODE = "en";

    private static final String CONF_GLOBAL_SECTION = "";

    public static final String CONF_BASEPATH = "conf/i18n/";

    public static final String CONF_BASEPATH_FLAGS = CONF_BASEPATH + "flags/";

    private static final String CONF_EXT = ".ini";

    private Ini iniLang;

    public static final String SEC_WMAIN = "W_MAIN";

    public static final String SEC_WABOUT = "W_ABOUT";

    public static final String SEC_WCHECKFORUPDATES = "W_CHECKFORUPDATES";

    public static final String SEC_DELETE = "W_DELETE";

    public static final String SEC_OPTIONS = "W_OPTIONS";

    public Translation(String localeCode) {
        try {
            iniLang = new Ini();
            String lngEncoding = I18nEncoding.getInstance().getLngEncoding(localeCode);
            iniLang.load(new File(CONF_BASEPATH + localeCode + ".ini"), lngEncoding);
            Log.info("Loaded language '" + localeCode + "' with " + lngEncoding + " encoding");
        } catch (IOException | PatternSyntaxException | InfinitiveLoopException ex) {
            Log.error(ex);
        }
    }

    /**
     * Get a translated string denoted by an identifier (in the global section).
     *
     * @param key the string identifier.
     * @return the translated string.
     */
    public String getString(String key) {
        return getString(CONF_GLOBAL_SECTION, key);
    }

    /**
     * Get a translated string denoted by an identifier and a section name.
     *
     * @param section
     * @param key the string identifier.
     * @return the translated string.
     */
    public String getString(String section, String key) {
        try {
            return iniLang.getKeyValue(" ??? ", section, key);
        } catch (CharConversionException | InfinitiveLoopException ex) {
            Log.error(ex);
            return " ??? ";
        }
    }

    public static List<CountryLanguage> getAvailLangList()
            throws IOException,
                   CharConversionException,
                   InfinitiveLoopException {
        List<CountryLanguage> langList = new ArrayList<>(2);
        File i18nFolder = new File(CONF_BASEPATH);
        File[] langFiles = i18nFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(CONF_EXT) && !name.equalsIgnoreCase("encoding.ini");
            }
        });
        Ini langIni = new Ini();
        for (File langFile : langFiles) {
            langIni.load(langFile, Main.CONF_ENCODING);
            String langDesc = langIni.getKeyValue(" ??? ", "", "name");
            String langFilename = langFile.getName();
            langFilename = langFilename.substring(0, langFilename.indexOf('.'));
            langList.add(new CountryLanguage(langFilename, langDesc));
        }
        return langList;
    }

    @SuppressWarnings("CallToThreadDumpStack")
    public static String getSystemLangIfAvailable(List<CountryLanguage> availLangList) {
        String sysLang = DEFAULT_LANGCODE;
        try {
            String userLanguage = System.getProperty("user.language");
            String userCountry = System.getProperty("user.country");
            String userLocale = userLanguage + "_" + userCountry;
            SEARCH_LANG:
            for (CountryLanguage lang : availLangList) {
                String langCode = lang.getCode();
                if (langCode.equalsIgnoreCase(userLocale) || langCode.equalsIgnoreCase(userLanguage)) {
                    sysLang = langCode;
                    break SEARCH_LANG;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            sysLang = DEFAULT_LANGCODE;
        }
        return sysLang;
    }
}
