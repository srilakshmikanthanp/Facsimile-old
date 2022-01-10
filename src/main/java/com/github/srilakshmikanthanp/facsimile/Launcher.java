package com.github.srilakshmikanthanp.facsimile;

import javafx.application.*;
import javafx.stage.*;

import com.github.kwhat.jnativehook.*;
import com.github.srilakshmikanthanp.facsimile.system.*;
import com.github.srilakshmikanthanp.facsimile.utility.*;

/**
 * Facsimile Application Launcher.
 */
public class Launcher extends Application {
    /**
     * Stater
     */
    @Override
    public void start(Stage pStage) {
        // get instance
        var facsimile = Facsimile.getInstance();
        var nkeyboard = NativeKeyboard.getInstance();
        var nMouseLis = NativeMouse.getInstance();
        var sTrayicon = SystemTrayIcon.getInstance();

        // set stage
        facsimile.setPrimaryStage(pStage);
        nkeyboard.setFacsimile(facsimile);
        nMouseLis.setFacsimile(facsimile);
        sTrayicon.setFacsimile(facsimile);

        // Register the Native Hook
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {   
            Utilityfuns.showError(e);
        }

        // add action listeners
        GlobalScreen.addNativeKeyListener(nkeyboard);
        GlobalScreen.addNativeMouseListener(nMouseLis);
    }

    /**
     * Stoper
     */
    @Override
    public void stop() {
        // UnRegister the Native Hook
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            Utilityfuns.showError(e);
        }

        // remove from tray
        SystemTrayIcon.getInstance().removeFromTray();
    }

    /**
     * Main function for application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
