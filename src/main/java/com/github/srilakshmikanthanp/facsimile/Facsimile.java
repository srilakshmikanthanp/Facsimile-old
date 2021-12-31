// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile;


import java.io.IOException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

import org.jnativehook.NativeHookException;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.dialog.*;
import com.github.srilakshmikanthanp.facsimile.panes.*;
import com.github.srilakshmikanthanp.facsimile.system.*;

/**
 * Main Stage for the Facsimile.
 */
class MainStage extends Stage
{
    // location to store data on production
    @SuppressWarnings("unused")
    private static final String PRD_LOC = System.getProperty(
        "user.home"
    );

    // location to store data on development
    @SuppressWarnings("unused")
    private static final String DEV_LOC = "./target";

    // dimension of application
    private static final double Width = 400, Height = 450;

    // mapping data for app
    private Mapping mapping;

    /**
     * Shows the try again prompt.
     */
    private boolean showTryAgain()
    {
        // create alert
        var alert = new Alert(AlertType.INFORMATION);

        // init the alert
        alert.setTitle("Invalid Password");
        alert.setHeaderText("Password is not correct");
        alert.setContentText("Are you want to try again ?");

        // show alert
        var res = alert.showAndWait();

        // if not valid
        if(!res.isPresent())
        {
            return false;
        }

        // return result
        return res.get() == ButtonType.OK;
    }

    /**
     * Shows the alert.
     */
    private boolean showError(String content)
    {
        // create alert
        var alert = new Alert(AlertType.ERROR);

        // init the alert
        alert.setTitle("Internal Error");
        alert.setHeaderText("Exception occured");
        alert.setContentText(content);

        // show alert
        var res =alert.showAndWait();

        // if not valid
        if(!res.isPresent())
        {
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
    private boolean loadPassword(CryptoEn cryptoEn)
    {
        while(!cryptoEn.isKeyExists())
        {
            // create dialog
            var dialog = new PassWordDialog(null,
                PassWordDialog.GINPUT_PASSWORD
            );

            // show dialog
            dialog.showAndWait();

            // if not valid
            if(!dialog.isOkay())
            {
                return false;
            }

            // get password
            var password = dialog.getActPassword();

            // load password
            try 
            {
                cryptoEn.loadExtistingKey(password);
            } 
            catch (IOException e) 
            {
                if(!this.showTryAgain()) // if try not again
                {
                    return false;
                }
            } 
            catch (GeneralSecurityException e) 
            {
                // create allert
                this.showError(e.getMessage());

                // return
                return false;
            }
        }

        return true;
    }

    /**
     * Create the password and key
     * 
     * @param cryptoEn engine
     * @return status
     */
    private boolean makePassword(CryptoEn cryptoEn)
    {
        while(!cryptoEn.isKeyExists())
        {
            // create dialog
            var dialog = new PassWordDialog(null, 
                PassWordDialog.CREATE_PASSWORD
            );

            // show dialog
            dialog.showAndWait();

            // if not valid
            if(!dialog.isOkay())
            {
                return false;
            }

            // get password
            var password = dialog.getActPassword();

            // load password
            try 
            {
                cryptoEn.createNewKey(password);
            } 
            catch (IOException e) 
            {
                // create allert
                this.showError(e.getMessage());

                // return
                return false;
            } 
            catch (GeneralSecurityException e) 
            {
                // create allert
                this.showError(e.getMessage());

                // return
                return false;
            }
        }

        return true;
    }

    /**
     * Make and test the Crypto Engine. 
     */
    private boolean CheckCrypto()
    {
        // crypro
        CryptoEn cryptoEn = mapping.getCryptoEn();

        // check if the crypto is valid
        if(cryptoEn.isKeyExists())
        {
           return true;
        }

        // is key file exits
        if(cryptoEn.isKeyFileExits())
        {
            return this.loadPassword(cryptoEn);
        }
        else
        {
            return this.makePassword(cryptoEn);
        }
    }

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

        // main pane
        var pane = new MainPane(mapping);

        // set scene
        this.setScene(
            new Scene(pane, Width, Height)
        );

        // set top
        this.setAlwaysOnTop(true);

        // center the stage
        this.setOnShown((evt) -> {
            this.centerOnScreen();
        });
    }

    /**
     * Sets visible or hide
     * 
     * @param visible true for Visible or false for hide
     */
    public void setVisible(boolean visible)
    {
        // define path
        var dPath = Paths.get(DEV_LOC, ".facsimile");

        // create dir
        dPath.toFile().mkdirs();

        // check the mapping
        if(!this.CheckCrypto())
        {
            return;
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
