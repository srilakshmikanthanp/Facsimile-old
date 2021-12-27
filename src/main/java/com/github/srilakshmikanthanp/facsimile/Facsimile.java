// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile;

import java.nio.file.Paths;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import org.jnativehook.NativeHookException;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.stages.*;
import com.github.srilakshmikanthanp.facsimile.system.*;
import com.github.srilakshmikanthanp.facsimile.utility.*;
 
/**
 * Main Panel for the Facsimile.
 */
class MainPane extends BorderPane
{
    // mappind data to app
    private Mapping mapping;

    /**
     * Constructor.
     */
    public MainPane(Mapping mapping)
    {
        this.mapping = mapping;
    }
}

/**
 * Main Stage for the Facsimile.
 */
class MainStage extends Stage
{
    // location to store data on production
    private static final String PRD_LOC = System.getProperty(
        "user.home"
    );

    // location to store data on development
    private static final String DEV_LOC = "target";

    // dimension of application
    private static final int Width = 800, Height = 600;

    // mapping data for app
    private Mapping mapping;

    // main pane of the application
    private MainPane mainPane;

    /**
     * Constructor
     * 
     * @param pStage Primary Stage
     */
    public MainStage(Stage pStage)
    {
        // init stage
        this.initOwner(pStage);
        this.initStyle(StageStyle.UNDECORATED);

        // create mapping
        this.mapping = new Mapping(
            Paths.get(DEV_LOC, ".facsimile")
        );
    }

    /**
     * Sets visible or hide
     * 
     * @param visible true for Visible or false for hide
     */
    public void setVisible(boolean visible)
    {
        // if main pane is null it is first time
        if(mainPane == null)
        {
            
        }

        // set visible or not
        if(visible)
        {
            this.show();
        }
        else
        {
            this.hide();
        }
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
            mainStage.setVisible(!mainStage.isShowing());
        });

        // initilize
        shortCut.setRunnable(runnable);

        // register the shortcut
        try 
        {
            shortCut.register();
        } 
        catch (NativeHookException e) 
        {
            // TODO show error
        }

        // add to system tray
        sysTray = SysTray.addToTray(runnable);
    }

    /**
     * Method that called on stop
     */
    @Override
    public void stop()
    {
        // unregister the shortcut
        try 
        {
            shortCut.unregister();
        } 
        catch (NativeHookException e) 
        {
            // TODO show error
        }

        // remove from tray
        sysTray.removeFromTray();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
