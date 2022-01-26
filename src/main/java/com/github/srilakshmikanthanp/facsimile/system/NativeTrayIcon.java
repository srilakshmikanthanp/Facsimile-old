package com.github.srilakshmikanthanp.facsimile.system;

import java.awt.*;
import java.net.URI;
import javax.swing.ImageIcon;
import javafx.application.Platform;
import com.github.srilakshmikanthanp.facsimile.utility.*;
import com.github.srilakshmikanthanp.facsimile.Facsimile;
import com.github.srilakshmikanthanp.facsimile.consts.*;

/**
 * System tray class
 */
public class NativeTrayIcon {
    // Singleton instance
    private static NativeTrayIcon instance = null;

    // icon for tray
    private TrayIcon icon = new TrayIcon(
            new ImageIcon(getClass().getResource("/images/logo.png")).getImage(),
            "FacSimile");

    // Tray instance
    private SystemTray tray = SystemTray.getSystemTray();

    // Runnable for tray menu
    private Facsimile facsimile;

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
    private NativeTrayIcon() {
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
            if (facsimile != null) {
                Platform.runLater(() -> {
                    facsimile.setVisible(true);
                });
            }
        });

        // about click listener
        about.addActionListener((evt) -> {
            this.openWebPage(AppConsts.APP_LINK);
        });

        // exit click listener
        exit.addActionListener((evt) -> {
            Platform.exit();
        });

        // icon click listener
        icon.addActionListener((evt) -> {
            if (facsimile != null) {
                Platform.runLater(() -> {
                    facsimile.setVisible(true);
                });
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
     * Set the Facsimile instance
     * 
     * @param facsimile instance
     */
    public void setFacsimile(Facsimile facsimile) {
        this.facsimile = facsimile;
    }

    /**
     * Removes the icon from system tray
     */
    public void removeFromTray() {
        tray.remove(icon);
    }

    /**
     * Adds Icon to tray
     */
    public static NativeTrayIcon getInstance() {
        if (instance == null) {
            instance = new NativeTrayIcon();
        }

        return instance;
    }
}
