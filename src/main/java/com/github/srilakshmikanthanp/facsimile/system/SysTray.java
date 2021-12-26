package com.github.srilakshmikanthanp.facsimile.system;

import java.awt.*;
import java.net.URI;
import javax.swing.ImageIcon;
import javafx.application.Platform;

/**
 * System tary class
 */
public class SysTray 
{
    // is tray added
    private static SysTray instance = null;

    // Tray instance
    private SystemTray tray = SystemTray.getSystemTray();

    // icon for tray
    private TrayIcon icon = new TrayIcon(
        new ImageIcon(getClass().getResource("/images/logo.png")).getImage(),
        "FacSimile"
    );

    // runnable
    private Runnable runnable = null;

    /**
     * Open the url in browser
     * 
     * @param url url to open
     */
    private void openWebPage(String url)
    {
        try 
        {
            Desktop.getDesktop().browse(new URI(url));
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Constructor
     */
    private SysTray(Runnable runnable)
    {
        // save runnable
        this.runnable = runnable;

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
            if(runnable != null) runnable.run();
        });

        about.addActionListener((evt) -> {
            this.openWebPage("https://github.com/srilakshmikanthanp/Facsimile");
        });

        exit.addActionListener((evt) -> {
            Platform.exit();
        });

        // set pop up menu to icon
        icon.setPopupMenu(menu);
        icon.setImageAutoSize(true);

        // try to add icon
        try 
        {
            tray.add(icon);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Removes the icon from system tray
     */
    public void removeFromSystemTray()
    {
        tray.remove(icon);
    }

    /**
     * Adds Icon to tray
     */
    public static SysTray addToSystemTray(Runnable runnable)
    {
        if(instance == null)
        {
            instance = new SysTray(runnable);
        }

        return instance;
    }
}
