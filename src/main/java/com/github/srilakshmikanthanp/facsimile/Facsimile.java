// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.shape.Rectangle;

import jfxtras.styles.jmetro.JMetroStyleClass;

import com.github.kwhat.jnativehook.*;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.dialog.*;
import com.github.srilakshmikanthanp.facsimile.panes.*;
import com.github.srilakshmikanthanp.facsimile.system.*;
import com.github.srilakshmikanthanp.facsimile.utility.Utilityfuncs;

/**
 * Main Stage for the Facsimile.
 */
class MainStage extends Stage {
    // location to store data on production
    @SuppressWarnings("unused")
    private static final String PRD_LOC = System.getProperty(
            "user.home"
    );

    // location to store data on development
    @SuppressWarnings("unused")
    private static final String DEV_LOC = "./target";

    // location to store data
    @SuppressWarnings("unused")
    private static final String LOC = PRD_LOC;

    // dimension of application
    private static final double Width = 400, Height = 450;

    // PAssword Dialog
    private PassWordDialog dialog = new PassWordDialog(this);

    // mapping data for app
    private Mapping mapping;

    /**
     * Shows the try again prompt.
     */
    private boolean showTryAgain() {
        // create alert
        var alert = new Alert(AlertType.INFORMATION);

        // init the alert
        alert.setTitle("Invalid Password");
        alert.setHeaderText("Password is not correct");
        alert.setContentText("Are you want to try again ?");

        // show alert
        var res = alert.showAndWait();

        // if not valid
        if (!res.isPresent()) {
            return false;
        }

        // return result
        return res.get() == ButtonType.OK;
    }

    /**
     * Loads the password to crypto
     * 
     * @param cryptoEn crypto Engine
     * @return status
     */
    private boolean loadPassword(CryptoEn cryptoEn) {
        // if aldersy showing
        if (dialog.isShowing()) {
            return false;
        }

        // main loop
        while (!cryptoEn.isKeyExists()) {
            // init dialog
            dialog.setType(
                PassWordDialog.GINPUT_PASSWORD
            );

            // show dialog
            dialog.showAndWait();

            // if not valid
            if (!dialog.isOkay()) {
                return false;
            }

            // get password
            var password = dialog.getActPassword();

            // load password
            try {
                cryptoEn.loadExtistingKey(password);
            } catch (IOException e) {
                if (!this.showTryAgain()) // if try not again
                {
                    return false;
                }
            } catch (GeneralSecurityException e) {
                // create allert
                Utilityfuncs.showError(e);

                // return
                return false;
            }
        }

        // done
        return true;
    }

    /**
     * Create the password and key
     * 
     * @param cryptoEn engine
     * @return status
     */
    private boolean makePassword(CryptoEn cryptoEn) {
        // if aldersy showing
        if (dialog.isShowing()) {
            return false;
        }

        // main loop
        while (!cryptoEn.isKeyExists()) {
            // init dialog
            dialog.setType(
                PassWordDialog.CREATE_PASSWORD
            );

            // show dialog
            dialog.showAndWait();

            // if not valid
            if (!dialog.isOkay()) {
                return false;
            }

            // get password
            var password = dialog.getActPassword();

            // load password
            try {
                cryptoEn.createNewKey(password);
            } catch (GeneralSecurityException | IOException e) {
                // create allert
                Utilityfuncs.showError(e);
                // return
                return false;
            }
        }

        // done
        return true;
    }

    /**
     * Make and test the Crypto Engine.
     */
    private boolean CheckCrypto() {
        // crypro
        CryptoEn cryptoEn = mapping.getCryptoEn();

        // check if the crypto is valid
        if (cryptoEn.isKeyExists()) {
            return true;
        }

        // is key file exits
        if (cryptoEn.isKeyFileExits()) {
            return this.loadPassword(cryptoEn);
        } else {
            return this.makePassword(cryptoEn);
        }
    }

    /**
     * Constructor
     * 
     * @param pStage Primary Stage
     */
    public MainStage(Stage pStage) {
        // init stage
        this.initOwner(pStage);
        this.initStyle(StageStyle.TRANSPARENT);

        // create mapping
        this.mapping = new Mapping(
            Paths.get(LOC, ".facsimile")
        );

        // main pane
        var pane = new MainPane(mapping);

        // init style
        pane.getStyleClass().add(
            "stage-main-pane"
        );

        // set top
        this.setAlwaysOnTop(true);

        // center the stage
        this.setOnShown((evt) -> {
            Utilityfuncs.centerToScreen(this);
            this.requestFocus();
        });

        // stackpane
        var stackPane = new StackPane(
            pane
        );
        var scene = new Scene(
            stackPane, Width, Height
        );

        // init pane
        stackPane.setPadding(new Insets(20));
        scene.setFill(Color.TRANSPARENT);
        stackPane.getStyleClass().add(
            "stage-bg-pane"
        );

        // set scene
        this.setScene(scene);

        // set theme
        ThemeListener.setSystemTheme(
            this.getScene()
        );

        // jmetro style
        pane.getStyleClass().add(
            JMetroStyleClass.BACKGROUND
        );
    }

    /**
     * Sets visible or hide
     * 
     * @param visible true for Visible or false for hide
     */
    public void setVisible(boolean visible) {
        // define path
        var dPath = Paths.get(LOC, ".facsimile");

        // create dir
        dPath.toFile().mkdirs();

        // check the mapping
        if (!this.CheckCrypto()) {
            return;
        }

        // set visible or not
        if (visible) {
            this.show();
        } else {
            this.hide();
        }
    }
}

/**
 * Main Application class
 */
public class Facsimile extends Application {
    // System Mouse listener
    private MouseListener sysMouse = new MouseListener(null);

    // shortcut listener
    private KeyBoardListener shortCut = new KeyBoardListener(null);

    // add to system tray
    private SystemTrayIcon sysTray;

    // MainStage
    private MainStage mainStage;

    /**
     * GLobal mouse pressed
     */
    private void globalMouseAction(int x, int y) {
        // Rectangle
        var rect = new Rectangle(
            mainStage.getX(),
            mainStage.getY(),
            mainStage.getWidth(),
            mainStage.getHeight()
        );

        var sclX = Screen.getPrimary().getOutputScaleX();
        var sclY = Screen.getPrimary().getOutputScaleY();
        var posX = x / sclX;
        var posY = y / sclY;

        // if not in stage
        if (!rect.contains(posX, posY)) {
            Platform.runLater(mainStage::hide);
        }
    }

    /**
     * Method that called on start
     */
    @Override
    public void start(Stage pStage) {
        // init primary stage
        pStage.initStyle(StageStyle.UTILITY);
        pStage.setMaxHeight(0.0);
        pStage.setMaxWidth(0.0);
        pStage.setOpacity(0.0);
        pStage.show();

        // define vars
        mainStage = new MainStage(pStage);

        // Runnable instance to visibe or not shortcut
        Runnable shortcutRun = () -> Platform.runLater(() -> {
            mainStage.setVisible(!mainStage.isShowing());
        });

        // set runnable for shortcut
        shortCut.setRunnable(shortcutRun);

        // set runnable for system mouse
        sysMouse.setActionListener(
            this::globalMouseAction
        );

        // register the shortcut
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            Utilityfuncs.showError(e);
        }

        // add listeners
        GlobalScreen.addNativeKeyListener(shortCut);
        GlobalScreen.addNativeMouseListener(sysMouse);
        GlobalScreen.addNativeMouseWheelListener(sysMouse);

        // add to system tray
        sysTray = SystemTrayIcon.addToTray(shortcutRun);
    }

    /**
     * Method that called on stop
     */
    @Override
    public void stop() {
        // unregister the Global Listeners
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            Utilityfuncs.showError(e);
        }

        // remove from tray
        sysTray.removeFromTray();
    }

    /**
     * Main method to start the application
     * 
     * @param args cmd args
     */
    public static void main(String[] args) {
        if(!Utilityfuncs.isApplicationRunning()) {
            launch(args);
        }
    }
}
