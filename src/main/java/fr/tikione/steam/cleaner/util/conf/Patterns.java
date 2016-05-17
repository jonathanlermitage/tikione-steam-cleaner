package fr.tikione.steam.cleaner.util.conf;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.ini.Ini;
import fr.tikione.steam.cleaner.Main;
import fr.tikione.steam.cleaner.util.Log;
import fr.tikione.steam.cleaner.util.Redist;
import java.io.CharConversionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configuration handler.
 */
public class Patterns {
	
		public static final String REMOTE_DEFINITION_FILES_SEPARATOR = "##n##"; 

    /** INI configuration file section : redistributable packages file and folder patterns. */
    private static final String CONFIG_REDIST_PATTERNS = "REDISTRIBUTABLE_PACKAGES_PATTERNS";

    /** INI configuration file key : redistributable package file patterns. */
    private static final String CONFIG_REDIST_PATTERNS__FILE_PATTERNS = "redistFilePatterns";

    /** INI configuration file key : redistributable package folder patterns. */
    private static final String CONFIG_REDIST_PATTERNS__FOLDER_PATTERNS = "redistFolderPatterns";

    /** INI configuration file key : redistributable package file patterns. */
    private static final String CONFIG_REDIST_PATTERNS__FILE_PATTERNS_EXP = "experimentalRedistFilesPatterns";

    /** INI configuration file key : redistributable package folder patterns. */
    private static final String CONFIG_REDIST_PATTERNS__FOLDER_PATTERNS_EXP = "experimentalRedistFolderPatterns";

    /** INI configuration file key : enable experimental patterns. */
    private static final String CONFIG_REDIST_PATTERNS__ENABLE_EXP_PATTERNS = "enableExperimentalPatterns";
		
		public static final String CONFIG_FILENAME = "tikione-steam-cleaner_patterns_rev244.ini";
		
    /** Singleton handler. */
    private static final Patterns config;

    /** File to use for configuration loading and saving. */
    private final File configFile;

    /** Configuration object. */
    private final Ini ini;
		
		private final List<Ini> inis;

    private boolean updated = false;

    static {
        // Singleton creation.
        try {
            config = new Patterns();
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
    public static synchronized Patterns getInstance() {
        return config;
    }

    /**
     * Load application configuration file. A default configuration file is created if necessary.
     *
     * @throws IOException if an I/O error occurs while retrieving the internal default configuration file, or while writing this default
     * configuration file.
     */
    private Patterns()
            throws IOException, CharConversionException, InfinitiveLoopException {
        File backupConfigFile = new File("conf/backup/tikione-steam-cleaner_patterns.ini");
        File userprofile = new File(Config.getProfilePath());
        userprofile.mkdirs();
        configFile = new File(userprofile.getAbsolutePath() + "/" + CONFIG_FILENAME);
        if (!configFile.exists()) {
            org.apache.commons.io.FileUtils.copyFile(backupConfigFile, configFile);
        }
        ini = new Ini();
        ini.getConfig().enableParseLineConcat(true);
        ini.getConfig().enableReadUnicodeEscConv(true);
        ini.load(configFile, Main.CONF_ENCODING);
				
				inis = new ArrayList<>(8);
				inis.add(ini);
				inis.addAll(RemotePatterns.getInis());
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

    public List<Redist> getRedistFilePatternsAndDesc(boolean includeExp)
            throws CharConversionException,
            InfinitiveLoopException {
        List<Redist> res = getRedistPatternsAndDesc(CONFIG_REDIST_PATTERNS__FILE_PATTERNS);
        if (includeExp) {
            List<Redist> resAdd = getRedistPatternsAndDesc(CONFIG_REDIST_PATTERNS__FILE_PATTERNS_EXP);
            res.addAll(resAdd);
        }
        return res;
    }

    public List<Redist> getRedistFolderPatternsAndDesc(boolean includeExp)
            throws CharConversionException,
            InfinitiveLoopException {
        List<Redist> res = getRedistPatternsAndDesc(CONFIG_REDIST_PATTERNS__FOLDER_PATTERNS);
        if (includeExp) {
            List<Redist> resAdd = getRedistPatternsAndDesc(CONFIG_REDIST_PATTERNS__FOLDER_PATTERNS_EXP);
            res.addAll(resAdd);
        }
        return res;
    }

    public boolean getEnableExperimentalPatterns()
            throws CharConversionException,
            InfinitiveLoopException {
        return Boolean.parseBoolean(ini.getKeyValue("false", CONFIG_REDIST_PATTERNS, CONFIG_REDIST_PATTERNS__ENABLE_EXP_PATTERNS));
    }

    private List<Redist> getRedistPatternsAndDesc(String configKey)
            throws CharConversionException,
            InfinitiveLoopException {
        List<Redist> redistList = new ArrayList<>(8);
				List<String> redistTokens = new ArrayList<>(64);
				for (Ini singleIni : inis) {
					String[] tokens = singleIni.getKeyValue("", CONFIG_REDIST_PATTERNS, configKey).split("\"", 0);
					redistTokens.addAll(Arrays.asList(tokens));
				}
        boolean FileDescToggle = true;
        String redistName = null;
        String redtsiDescription;
        for (String redist : redistTokens) {
            if (redist.length() > 0) { // Skip first and last tokens.
                if (FileDescToggle) {
                    redistName = redist;
                } else {
                    redtsiDescription = redist;
                    redistList.add(new Redist(redistName, redtsiDescription));
                }
                FileDescToggle ^= true;
            }
        }
        return redistList;
    }

    public void setEnableExperimentalPatterns(boolean enable) {
        updated = true;
        ini.setKeyValue(CONFIG_REDIST_PATTERNS, CONFIG_REDIST_PATTERNS__ENABLE_EXP_PATTERNS, Boolean.toString(enable));
    }
}
