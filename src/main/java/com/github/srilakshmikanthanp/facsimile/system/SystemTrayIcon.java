package com.github.srilakshmikanthanp.facsimile.system;

import java.awt.*;
import java.net.URI;
import javax.swing.ImageIcon;
import javafx.application.Platform;

/**
 * System tary class
 */
public class SystemTrayIcon {
    // application Link
    private static final String APP_LINK = ("https://github.com/srilakshmikanthanp/Facsimile");

    // is tray added
    private static SystemTrayIcon instance = null;

    // Tray instance
    private SystemTray tray = SystemTray.getSystemTray();

    // icon for tray
    private TrayIcon icon = new TrayIcon(
        new ImageIcon(getClass().getResource("/images/logo.png")).getImage(),
        "FacSimile"
    );

    /**
     * Open the url in browser
     * 
     * @param url url to open
     */
    private void openWebPage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor
     */
    private SystemTrayIcon(Runnable runnable) {
        // define vars
        var menu = new PopupMenu();
        var app = new MenuItem("FacSimile");
        var about = new MenuItem("About");
        var exit = new MenuItem("Exit");

        // add menu item
        menu.add(app);
        menu.add(about);
        menu.addSeparator();
        menu.add(exit);

        // add listeners
        app.addActionListener((evt) -> {
            if (runnable != null)
                runnable.run();
        });

        about.addActionListener((evt) -> {
            this.openWebPage(APP_LINK);
        });

        exit.addActionListener((evt) -> {
            Platform.exit();
        });

        // set pop up menu to icon
        icon.setPopupMenu(menu);
        icon.setImageAutoSize(true);

        // try to add icon
        try {
            tray.add(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds Icon to tray
     */
    public static SystemTrayIcon addToTray(Runnable runnable) {
        if (instance == null) {
            instance = new SystemTrayIcon(runnable);
        }

        return instance;
    }

    /**
     * Removes the icon from system tray
     */
    public void removeFromTray() {
        tray.remove(icon);
    }
}