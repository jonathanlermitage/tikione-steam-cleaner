package fr.tikione.steam.cleaner.util;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.steam.cleaner.Version;
import fr.tikione.steam.cleaner.util.conf.Config;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Manager for program's updates.
 */
public class UpdateManager {

	/** Suppresses default constructor, ensuring non-instantiability. */
	private UpdateManager() {
	}

	/**
	 * Get the latest program's online version number.
	 *
	 * @return latest version number.
	 */
	public static String getLatestVersion() {
		String latestVersion;
		try {
			URL url = new URL(Config.getInstance().getLatestVersionUrl());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String line = rd.readLine();
				if (line != null) {
					latestVersion = line;
					if (latestVersion.startsWith("fr.tikione.steam.cleaner=")) {
						latestVersion = latestVersion.substring("fr.tikione.steam.cleaner=".length());
					} else {
						Log.error(new java.io.StreamCorruptedException("Bad online version file"));
						latestVersion = Version.VERSION;
					}
				} else {
					Log.error(new java.io.StreamCorruptedException("Empty online version file"));
					latestVersion = Version.VERSION;
				}
			}
		} catch (ConnectException | UnknownHostException ex) {
			Log.info("Cannot contact the update center");
			latestVersion = Version.VERSION;
		} catch (InfinitiveLoopException | IOException ex) {
			Log.error(ex);
			latestVersion = Version.VERSION;
		}
		return latestVersion;
	}

	/**
	 * Indicates if the current program's version number is up to date, compared to the given one.
	 *
	 * @param latestVersion version number to compare current with.
	 * @return {@code true} if current version is up to date, otherwise {@code false}.
	 */
	public static boolean IsUpToDate(String latestVersion) {
		int c1 = Version.MAJOR;
		int c2 = Version.MINOR;
		int c3 = Version.PATCH;
		String[] asOnlineVersion = normalizeVersionStr(latestVersion).split("\\.", -1);
		int o1 = Integer.parseInt(asOnlineVersion[0]);
		int o2 = Integer.parseInt(asOnlineVersion[1]);
		int o3 = Integer.parseInt(asOnlineVersion[2]);
		int iCurrentVersion = c3 + 1_000 * c2 + 1_000_000 * c1;
		int iOnlineVersion = o3 + 1_000 * o2 + 1_000_000 * o1;
		return iCurrentVersion >= iOnlineVersion;
	}

	/**
	 * Normalize a version number. Per example, "01.08.10" becomes "1.8.10".
	 *
	 * @param version version number to normalize.
	 * @return normalized version number.
	 */
	public static String normalizeVersionStr(String version) {
		String[] currentVersion = version.split("\\.", -1);
		int v1 = Integer.parseInt(currentVersion[0]);
		int v2 = Integer.parseInt(currentVersion[1]);
		int v3 = Integer.parseInt(currentVersion[2]);
		return v1 + "." + v2 + "." + v3;
	}

	/**
	 * Launch the external web browser with the TikiOne Steam Cleaner download URL (on the SourceForge.net website).
	 */
	public static void extBrowserGetLatestVersion() {
		extBrowser("http://sourceforge.net/projects/tikione/files/latest/download");
	}

	/**
	 * Launch the external web browser with the TikiOne Steam Cleaner changelog file URL (on the TikiOne.fr website).
	 */
	public static void extBrowserGetLatestChangelog() {
		extBrowser("https://github.com/jonathanlermitage/tikione-steam-cleaner/blob/master/CHANGELOG.md");
	}

	/**
	 * Launches the default browser to display a URI.
	 *
	 * @param uri the URI to display.
	 */
	public static void extBrowser(String uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(uri));
			} catch (URISyntaxException | IOException ex) {
				Log.error(ex);
			}
		}
	}
}
