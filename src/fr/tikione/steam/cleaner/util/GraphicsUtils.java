package fr.tikione.steam.cleaner.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.net.URL;

/**
 * GUI utilities.
 */
public class GraphicsUtils {

    /** Suppresses default constructor, ensuring non-instantiability. */
    private GraphicsUtils() {
    }

    /**
     * Center a frame.
     *
     * @param window the frame to center.
     */
    public static void setFrameCentered(Window window) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        final int screenWidth = screenSize.width;
        final int screenHeight = screenSize.height;
        int posX = (screenWidth / 2) - (window.getWidth() / 2);
        int posY = (screenHeight / 2) - (window.getHeight() / 2);
        window.setBounds(posX, posY, window.getWidth(), window.getHeight());
    }

    /**
     * Set a frame's icon.
     *
     * @param window the frame to set icon.
     */
    public static void setIcon(Window window) {
        URL url = GraphicsUtils.class.getResource("/fr/tikione/steam/cleaner/gui/icons/tikione-steam-cleaner-icon.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        window.setIconImage(img);
    }
}
