package fr.tikione.steam.cleaner.util.conf;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.ini.Ini;
import fr.tikione.steam.cleaner.Main;
import fr.tikione.steam.cleaner.util.CountryLanguage;
import fr.tikione.steam.cleaner.util.Log;
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

    /** INI configuration file key : list of remote redist definition files. */
    private static final String CONFIG_MISC__REMOTE_DEFINITION_FILES = "remoteDefinitionFiles";

    /** Singleton handler. */
    private static final Config config;

		/** Default remote definition file. */
		private static final String DEFAULT_REMOTE_DEFINITION_FILE = "https://raw.githubusercontent.com/jonathanlermitage/"
						+ "tikione-steam-cleaner/master/dist2/conf/backup/tikione-steam-cleaner_patterns.ini";
		
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
        File userprofile = new File(getProfilePath());
        userprofile.mkdirs();
        configFile = new File(userprofile.getAbsolutePath() + "/tikione-steam-cleaner_config_rev241.ini");
        if (!configFile.exists()) {
            org.apache.commons.io.FileUtils.copyFile(backupConfigFile, configFile);
        }
        ini = new Ini();
        ini.getConfig().enableParseLineConcat(true);
        ini.getConfig().enableReadUnicodeEscConv(true);
        ini.load(configFile, Main.CONF_ENCODING);
    }
	
	/**
	 * Get profile path.
	 * @return profile path.
	 */
	public static String getProfilePath() {
		if (Main.ARG_PORTABLE) {
			return "./.tikione/";
		} else {
			return System.getenv("USERPROFILE") + "/.tikione/";
		}
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

    public String getRemoteDefinitionFiles()
            throws CharConversionException,
            InfinitiveLoopException {
        return ini.getKeyValue(DEFAULT_REMOTE_DEFINITION_FILE, CONFIG_MISC, CONFIG_MISC__REMOTE_DEFINITION_FILES);
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

		public void setRemoteDefinitionFiles(String urls) {
        updated = true;
        ini.setKeyValue(CONFIG_MISC, CONFIG_MISC__REMOTE_DEFINITION_FILES, urls);
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

    public void setSelectedLanguage(String language) {
        updated = true;
        ini.setKeyValue(CONFIG_LANG, CONFIG_LANG__SELECTION, language);
    }

    public String getLatestVersionUrl()
            throws CharConversionException,
            InfinitiveLoopException {
        return "https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/uc/latest_version.txt";
    }
}
