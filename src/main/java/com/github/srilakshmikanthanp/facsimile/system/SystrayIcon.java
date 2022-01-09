package com.github.srilakshmikanthanp.facsimile.system;

import java.awt.*;
import java.net.URI;
import javax.swing.ImageIcon;
import javafx.application.Platform;
import com.github.srilakshmikanthanp.facsimile.utility.*;

/**
 * System tray class
 */
public class SystrayIcon {
    // application Link
    private static final String APP_LINK = "https://github.com/srilakshmikanthanp/Facsimile";

    // icon for tray
    private TrayIcon icon = new TrayIcon(
        new ImageIcon(getClass().getResource("/images/logo.png")).getImage(),
        "FacSimile"
    );

    // Tray instance
    private SystemTray tray = SystemTray.getSystemTray();

    // Runnable for tray menu
    private Runnable runnable;

    // is tray added
    private static SystrayIcon instance = null;

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
    private SystrayIcon(Runnable run) {
        // innit runnable
        this.runnable = run;

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

        // app click listeners
        app.addActionListener((evt) -> {
            if (runnable != null) {
                runnable.run();
            }
        });

        // about click listener
        about.addActionListener((evt) -> {
            this.openWebPage(APP_LINK);
        });

        // exit click listener
        exit.addActionListener((evt) -> {
            Platform.exit();
        });

        // icon click listener
        icon.addActionListener((evt) -> {
            if (runnable != null) {
                runnable.run();
            }
        });

        // set pop up menu to icon
        icon.setPopupMenu(menu);
        icon.setImageAutoSize(true);

        // try to add icon
        try {
            tray.add(icon);
        } catch (Exception e) {
            Utilityfuns.showError(e);
        }
    }

    /**
     * Adds Icon to tray
     */
    public static SystrayIcon addWithAction(Runnable runnable) {
        if (instance == null) {
            instance = new SystrayIcon(runnable);
        } else {
            instance.runnable = runnable;
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
