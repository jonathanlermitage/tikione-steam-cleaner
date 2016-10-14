package fr.tikione.steam.cleaner.util.conf;

import fr.tikione.ini.Ini;
import fr.tikione.steam.cleaner.Main;
import fr.tikione.steam.cleaner.util.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fr.tikione.steam.cleaner.util.conf.Config.getProfilePath;

/**
 * Configuration handler for remote Redist definitions files.
 */
public class RemotePatterns {
    
    public static final String CONFIG_FOLDER = "remoteRedistDefinitions";
    
    /**
     * Store new remote Redist definitions files and remove olderones.
     * @param contents content of new files.
     * @throws IOException if an IO error occurs.
     */
    public static void store(List<String> contents) throws IOException {
        File thirdpartyFiles = new File(getProfilePath(), CONFIG_FOLDER);
        thirdpartyFiles.mkdirs();
        File[] oldFiles = thirdpartyFiles.listFiles();
        for (File oldFile : oldFiles) {
            oldFile.delete();
        }
        thirdpartyFiles.mkdirs();
        for (String content : contents) {
            File configFile = new File(thirdpartyFiles, System.nanoTime() + ".ini");
            FileUtils.write(configFile, content);
        }
    }
    
    /**
     * Get all Ini related to remote Redist definitions files recently downloaded.
     * @return Ini objects.
     */
    public static List<Ini> getInis() {
        List<Ini> inis = new ArrayList<>(8);
        File thirdpartyFiles = new File(getProfilePath(), CONFIG_FOLDER);
        if (thirdpartyFiles.exists()) {
            File[] iniFiles = thirdpartyFiles.listFiles();
            for (File iniFile : iniFiles) {
                try {
                    Ini ini = new Ini();
                    ini.getConfig().enableParseLineConcat(true);
                    ini.getConfig().enableReadUnicodeEscConv(true);
                    ini.load(iniFile, Main.CONF_ENCODING);
                    inis.add(ini);
                } catch (IOException ex) {
                    Log.error("cannot load or parse ini file: " + iniFile.getAbsolutePath(), ex);
                }
            }
        }
        return inis;
    }
}
