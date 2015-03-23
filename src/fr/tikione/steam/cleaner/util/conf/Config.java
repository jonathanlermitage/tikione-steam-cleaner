package fr.tikione.steam.cleaner.util.conf;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.ini.Ini;
import fr.tikione.steam.cleaner.Main;
import fr.tikione.steam.cleaner.util.CountryLanguage;
import fr.tikione.steam.cleaner.util.Log;
import fr.tikione.steam.cleaner.util.Redist;
import fr.tikione.steam.cleaner.util.Translation;
import java.io.CharConversionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Configuration handler.
 */
public class Config {

    /** INI configuration file section : Steam folders. */
    private static final String CONFIG_STEAM_FOLDERS = "STEAM_FOLDERS";

    /** INI configuration file key : possible Steam folders. */
    private static final String CONFIG_STEAM_FOLDERS__POSSIBLE_DIRS = "possibleSteamPaths";

    /** INI configuration file key : latest Steam folder used in application. */
    private static final String CONFIG_STEAM_FOLDERS__LATEST_DIR = "latestSteamPath";

    /** INI configuration file key : max depth in folders recursive search. */
    private static final String CONFIG_STEAM_FOLDERS__MAX_DEPTH = "maxDepth";

    /** INI configuration file section : UI settings. */
    private static final String CONFIG_MAIN_WINDOW_UI = "MAIN_WINDOW_UI";

    /** INI configuration file key : latest Window length. */
    private static final String CONFIG_MAIN_WINDOW_UI__SIZE_X = "latestLength";

    /** INI configuration file key : latest Window height. */
    private static final String CONFIG_MAIN_WINDOW_UI__SIZE_Y = "latestHeight";

    /** INI configuration file key : indicate if window is maximized. */
    private static final String CONFIG_MAIN_WINDOW_UI__STATE = "state";

    /** INI configuration file section : UI language. */
    private static final String CONFIG_LANG = "LANG";

    /** INI configuration file key : the selected UI language. */
    private static final String CONFIG_LANG__SELECTION = "selected";

    /** INI configuration file section : misc. */
    private static final String CONFIG_MISC = "MISC";

    /** INI configuration file key : check for updates at startup. */
    private static final String CONFIG_MISC__CHK_FOR_UPT_AT_STATUP = "checkForUpdatesAtStartup";

    /** Singleton handler. */
    private static final Config config;

    /** File to use for configuration loading and saving. */
    private final File configFile;

    /** Configuration object. */
    private final Ini ini;

    private boolean updated = false;

    static {
        // Singleton creation.
        try {
            config = new Config();
        } catch (IOException | InfinitiveLoopException ex) {
            Log.error(ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get the configuration handler as a singleton.
     *
     * @return the configuration handler singleton.
     */
    public static synchronized Config getInstance() {
        return config;
    }

    /**
     * Load application configuration file. A default configuration file is created if necessary.
     *
     * @throws IOException if an I/O error occurs while retrieving the internal default configuration file, or while writing this default
     * configuration file.
     */
    private Config()
            throws IOException, CharConversionException, InfinitiveLoopException {
        File backupConfigFile = new File("conf/backup/tikione-steam-cleaner_config.ini");
        File userprofile = new File(System.getenv("USERPROFILE") + "/.tikione/");
        userprofile.mkdirs();
        configFile = new File(userprofile.getAbsolutePath() + "/tikione-steam-cleaner_config_rev241.ini");
        if (!configFile.exists()) {
            org.apache.commons.io.FileUtils.copyFile(backupConfigFile, configFile);
        }
        ini = new Ini();
        ini.getConfig().enableParseLineConcat(true);
        ini.getConfig().enableReadUnicodeEscConv(true);
        ini.load(configFile, Main.CONF_ENCODING);

//        // Patch for 2.3.1 revision: migrate previous profile to new one.
//        try {
//            File configFileP231 = new File(userprofile.getAbsolutePath() + "/tikione-steam-cleaner_config.ini");
//            if (configFileP231.exists()) {
//                Ini iniP231 = new Ini();
//                iniP231.getConfig().enableParseLineConcat(true);
//                iniP231.getConfig().enableReadUnicodeEscConv(true);
//                iniP231.load(configFileP231, Main.CONF_ENCODING);
//                ini.setKeyValue(
//                        CONFIG_STEAM_FOLDERS, CONFIG_STEAM_FOLDERS__LATEST_DIR,
//                        iniP231.getKeyValue(CONFIG_STEAM_FOLDERS, CONFIG_STEAM_FOLDERS__LATEST_DIR));
//                ini.setKeyValue(
//                        CONFIG_STEAM_FOLDERS, CONFIG_STEAM_FOLDERS__MAX_DEPTH,
//                        iniP231.getKeyValue(CONFIG_STEAM_FOLDERS, CONFIG_STEAM_FOLDERS__MAX_DEPTH));
//                ini.setKeyValue(
//                        CONFIG_LANG, CONFIG_LANG__SELECTION,
//                        iniP231.getKeyValue(CONFIG_LANG, CONFIG_LANG__SELECTION));
//                ini.setKeyValue(
//                        CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__SIZE_X,
//                        iniP231.getKeyValue(CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__SIZE_X));
//                ini.setKeyValue(
//                        CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__SIZE_Y,
//                        iniP231.getKeyValue(CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__SIZE_Y));
//                ini.setKeyValue(
//                        CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__STATE,
//                        iniP231.getKeyValue(CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__STATE));
//                ini.setKeyValue(
//                        CONFIG_MISC, CONFIG_MISC__CHK_FOR_UPT_AT_STATUP,
//                        iniP231.getKeyValue(CONFIG_MISC, CONFIG_MISC__CHK_FOR_UPT_AT_STATUP));
//                configFileP231.delete();
//            }
//        } catch (InfinitiveLoopException | IOException | IllegalArgumentException ex) {
//            Log.error(ex);
//        }
    }

    /**
     * Saves the configuration into a file.
     *
     * @throws IOException if an I/O error occurs while writing the file.
     */
    public void save()
            throws IOException {
        if (updated) {
            ini.store(configFile, Main.CONF_ENCODING, Main.CONF_NEWLINE);
        }
    }

    public List<String> getPossibleSteamFolders()
            throws CharConversionException,
            InfinitiveLoopException {
        String folderList = ini.getKeyValue("/", CONFIG_STEAM_FOLDERS, CONFIG_STEAM_FOLDERS__POSSIBLE_DIRS);
        String[] patterns = folderList.split(Matcher.quoteReplacement(";"), 0);
        List<String> allSteamPaths = new ArrayList<>(80);
        File[] roots = File.listRoots();
        String[] driveLetters = new String[roots.length];
        for (int nRoot = 0; nRoot < roots.length; nRoot++) {
            driveLetters[nRoot] = roots[nRoot].getAbsolutePath();
        }
        String varDriveLetter = /* Matcher.quoteReplacement( */ "{drive_letter}"/* ) */;
        for (String basePattern : patterns) {
            for (String driveLetter : driveLetters) {
                allSteamPaths.add(basePattern.replace(varDriveLetter, driveLetter));
            }
        }
        Collections.addAll(allSteamPaths, patterns);
        return allSteamPaths;
    }

    public boolean getCheckForUpdatesAtStartup()
            throws CharConversionException,
            InfinitiveLoopException {
        return Boolean.parseBoolean(ini.getKeyValue("false", CONFIG_MISC, CONFIG_MISC__CHK_FOR_UPT_AT_STATUP));
    }

    public String getLatestSteamFolder()
            throws CharConversionException,
            InfinitiveLoopException {
        return ini.getKeyValue("", CONFIG_STEAM_FOLDERS, CONFIG_STEAM_FOLDERS__LATEST_DIR);
    }

    public int getMaDepth()
            throws CharConversionException,
            InfinitiveLoopException {
        return Integer.parseInt(ini.getKeyValue("", CONFIG_STEAM_FOLDERS, CONFIG_STEAM_FOLDERS__MAX_DEPTH));
    }

    public int getUILatestWidth()
            throws CharConversionException,
            InfinitiveLoopException {
        return Integer.parseInt(ini.getKeyValue("750", CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__SIZE_X));
    }

    public int getUILatestHeight()
            throws CharConversionException,
            InfinitiveLoopException {
        return Integer.parseInt(ini.getKeyValue("500", CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__SIZE_Y));
    }

    public int getUIState()
            throws CharConversionException,
            InfinitiveLoopException {
        return Integer.parseInt(ini.getKeyValue("0", CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__STATE));
    }

	public void setCheckForUpdatesAtStartup(boolean checkForUpt) {
        updated = true;
        ini.setKeyValue(CONFIG_MISC, CONFIG_MISC__CHK_FOR_UPT_AT_STATUP, Boolean.toString(checkForUpt));
    }

    public void setLatestSteamFolder(String folder) {
        updated = true;
        ini.setKeyValue(CONFIG_STEAM_FOLDERS, CONFIG_STEAM_FOLDERS__LATEST_DIR, folder);
    }

    public void setUILatestWidth(int width) {
        updated = true;
        ini.setKeyValue(CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__SIZE_X, Integer.toString(width));
    }

    public void setUILatestHeight(int height) {
        updated = true;
        ini.setKeyValue(CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__SIZE_Y, Integer.toString(height));
    }

    public void setUIState(int state) {
        updated = true;
        ini.setKeyValue(CONFIG_MAIN_WINDOW_UI, CONFIG_MAIN_WINDOW_UI__STATE, Integer.toString(state));
    }

    public void setMaxDepth(int maxDepth) {
        updated = true;
        ini.setKeyValue(CONFIG_STEAM_FOLDERS, CONFIG_STEAM_FOLDERS__MAX_DEPTH, Integer.toString(maxDepth));
    }

    public String getSelectedLanguage()
            throws CharConversionException,
            InfinitiveLoopException,
            IOException {
        return getSelectedLanguage(Translation.getAvailLangList());
    }

    public String getSelectedLanguage(List<CountryLanguage> availLang)
            throws CharConversionException,
            InfinitiveLoopException,
            IOException {
        String lang = ini.getKeyValue("", CONFIG_LANG, CONFIG_LANG__SELECTION);
        if (fr.tikione.ini.util.StringHelper.strIsEmpty(lang)) {
            lang = Translation.getSystemLangIfAvailable(availLang);
        }
        return lang;
    }

    public void setSelecteLanguage(String language) {
        updated = true;
        ini.setKeyValue(CONFIG_LANG, CONFIG_LANG__SELECTION, language);
    }

    public String getLatestVersionUrl()
            throws CharConversionException,
            InfinitiveLoopException {
        // return ini.getKeyValue("?", CONFIG_UPDATECENTER, CONFIG_UPDATECENTER__LATEST_VERSION_URL);
        return "http://sourceforge.net/projects/tikione/files/tikione-steam-cleaner/uc/steamcleaner_latestversion.txt/download";
    }
}
