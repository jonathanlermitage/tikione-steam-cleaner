package fr.tikione.steam.cleaner;

import fr.tikione.ini.InfinitiveLoopException;
import fr.tikione.steam.cleaner.util.ThreadedFileComparator;
import fr.tikione.steam.cleaner.gui.dialog.JFrameMain;
import fr.tikione.steam.cleaner.util.Log;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Application launcher.
 */
public class Main {

    public static final String CONF_ENCODING = StandardCharsets.UTF_8.name();

    public static final Locale SYS_LOCALE = Locale.getDefault();

    public static final String CONF_NEWLINE = "\r\n";

    /**
     * The application launcher. Starts GUI.
     *
     * @param args command-line arguments. Not used in this version.
     */
    public static void main(String[] args) {
        try {
            Log.info("-------------------------------------------------");
            Log.info("Application started; version is " + Version.VERSION
                    + "; default encoding is " + CONF_ENCODING
                    + "; default locale is " + SYS_LOCALE.toString());
            Log.info("(DEBUG) Number of CPU logical cores detected is '" + ThreadedFileComparator.getCPUCores() + '\'');
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            Log.error(e);
        }
        try {
            JFrameMain gui = new JFrameMain();
            gui.setVisible(true);
        } catch (IOException | InfinitiveLoopException ex) {
            Log.error(ex);
        }
    }

    /** Suppresses default constructor, ensuring non-instantiability. */
    private Main() {
    }
}
