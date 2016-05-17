package fr.tikione.steam.cleaner.util.conf;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
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
}
