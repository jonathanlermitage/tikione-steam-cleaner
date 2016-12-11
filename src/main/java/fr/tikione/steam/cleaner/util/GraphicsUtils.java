package fr.tikione.steam.cleaner.util;

import java.awt.*;

/**
 * GUI utilities.
 */
public class GraphicsUtils {
    
    private static final Toolkit DEFAULT_TOOLKIT = Toolkit.getDefaultToolkit();
    
    /** Suppresses default constructor, ensuring non-instantiability. */
    private GraphicsUtils() {
    }
    
    /**
     * Center a frame.
     *
     * @param window the frame to center.
     */
    public static void setFrameCentered(Window window) {
        Dimension screenSize = DEFAULT_TOOLKIT.getScreenSize();
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
        window.setIconImage(DEFAULT_TOOLKIT.createImage(GraphicsUtils.class.getResource(
                "/fr/tikione/steam/cleaner/gui/icons/tikione-steam-cleaner-icon.png")));
    }
}
