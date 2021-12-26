// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.stages.*;
import com.github.srilakshmikanthanp.facsimile.system.*;
import com.github.srilakshmikanthanp.facsimile.utility.*;

import org.jnativehook.NativeHookException;

/**
 * Main Panel for the Facsimile.
 */
class MainPane extends BorderPane
{
    public MainPane(Stage pStage)
    {

    }
}

/**
 * Main Stage for the Facsimile.
 */
class MainStage extends Stage
{
    /**
     * Constructor
     * 
     * @param pStage PRimary Stage
     */
    public MainStage(Stage pStage)
    {
        // init stage
        this.initOwner(pStage);
        this.initStyle(StageStyle.UNDECORATED);
    }
}

/**
 * Main Application class
 */
public class Facsimile extends Application 
{
    // shortcut listener
    private ShortCut shortCut = new ShortCut(null);

    // add to system tray
    private SysTray sysTray;

    /**
     * Method that called on start
     */
    @Override
    public void start(Stage pStage) 
    {
        // init primary stage
        pStage.initStyle(StageStyle.UTILITY);
        pStage.setHeight(0.0);
        pStage.setWidth(0.0);
        pStage.setOpacity(0.0);
        pStage.show();

        // define vars
        var mainStage = new MainStage(pStage);

        // Runnable instance to start
        Runnable runnable = () -> Platform.runLater(() -> {
            if(!mainStage.isShowing()) {
                mainStage.show();
            }
        });

        // add to system tray
        sysTray = SysTray.addToSystemTray(runnable);

        // initilize
        shortCut.setRunnable(runnable);

        // register the shortcut
        try {
            shortCut.register();
        } catch (NativeHookException e) {
            // TODO show error
        }
    }

    /**
     * Method that called on stop
     */
    @Override
    public void stop()
    {
        // unregister the shortcut
        try {
            shortCut.unregister();
        } catch (NativeHookException e) {
            // TODO show error
        }

        // remove from tray
        sysTray.removeFromSystemTray();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
