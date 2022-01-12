package com.github.srilakshmikanthanp.facsimile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.function.BooleanSupplier;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.dialog.*;
import com.github.srilakshmikanthanp.facsimile.utility.*;

/**
 * Main Stage for the Application.
 */
public class Facsimile extends Stage {
    // dimension of application
    private static final double Stagewidth = 400, Stageheight = 450;

    // location to store data on production
    @SuppressWarnings("unused")
    private static final String PRD_LOC = System.getProperty("user.home");

    // location to store data on development
    @SuppressWarnings("unused")
    private static final String DEV_LOC = "./target";

    // location to store data
    private static final String LOC = PRD_LOC;

    // password input dialog for auth
    private static final InputPassword pwdDialog = new InputPassword(null);

    // password create dialog for auth
    private static final MakePassword mkPwdDialog = new MakePassword(null);

    // Facsimile instance
    private static Facsimile instance;

    // Crypto Hash Mapping
    private final CryptoMap cryptoMap = new CryptoMap(
        Path.of(LOC, ".facsimile")
    );

    /**
     * Create password for user
     */
    private boolean makePassword() {
        // if aldredy showing
        if (mkPwdDialog.isShowing()) {
            mkPwdDialog.toFront();
            return false;
        }

        // show dialog
        mkPwdDialog.showAndWait();

        // if not okay
        if (!mkPwdDialog.isOkay()) {
            return false;
        }

        // get the password
        var pass = mkPwdDialog.getNewPassword();

        // try to set password
        try {
            cryptoMap.makeCrypto(pass);
        } catch (IOException | GeneralSecurityException e) {
            Utilityfuns.showError(e);
            return false;
        }

        // sucess
        return true;
    }

    /**
     * Authenticate user
     */
    private boolean authPassword() {
        // if aldredy showing
        if (pwdDialog.isShowing()) {
            pwdDialog.toFront();
            return false;
        }

        // show dialog and status
        BooleanSupplier isOkay = () -> {
            pwdDialog.showAndWait();
            return pwdDialog.isOkay();
        };

        // loop
        while (isOkay.getAsBoolean()) {
            try {
                var pass = pwdDialog.getPassword();
                cryptoMap.loadCrypto(pass);
        
                try {
                    cryptoMap.loadJson();
                } catch (IOException e) {
                    System.err.println(e.toString());
                } catch (GeneralSecurityException e) {
                    Utilityfuns.showError(e);
                }
                
                return true;
            } catch (IOException e) {
                pwdDialog.setError(true);
                pwdDialog.setLabel("Invalid Auth");
            } catch (GeneralSecurityException e) {
                Utilityfuns.showError(e);
                return false;
            }
        }

        // sucess
        return false;
    }

    /**
     * Check for Authentication
     */
    private boolean readyAuth() {
        // If key exits then all set
        if (cryptoMap.isSecrectKeyExits()) {
            return true;
        }

        if (cryptoMap.isKsFileExists()) {
            return this.authPassword();
        } else {
            return this.makePassword();
        }
    }

    /**
     * Inits the Primary Stage
     * 
     * @param pStage stage
     */
    private void stageInit(Stage pStage) {
        pStage.initStyle(StageStyle.UTILITY);
        pStage.setMaxHeight(0);
        pStage.setMaxWidth(0);
        pStage.setOpacity(0);
        pStage.setX(Double.MAX_VALUE);
        pStage.show();
    }

    /**
     * Constructor For Facsimile.
     * 
     * @param pStage Stage
     */
    private Facsimile() {
        // app pane
        var pane = new AppPane(cryptoMap);
        pane.getStyleClass().add("main-pane");
        var stkPane = new StackPane(pane);
        stkPane.setPadding(new Insets(10));
        stkPane.getStyleClass().add("container");

        // set ecene for stage
        var scene = new Scene(stkPane);
        scene.setFill(Color.TRANSPARENT);
        this.setScene(scene);

        // init the stage
        this.setAlwaysOnTop(true);
        this.setOnShown((evt) -> {
            this.setHeight(Stageheight);
            this.setWidth(Stagewidth);
            Utilityfuns.centerToScreen(this);
        });

        // init the theme
        Preference.addPreferenceChangeListener((evt) -> {
            if(evt.getKey() == Preference.THEME_KEY) {
                Platform.runLater(() -> {
                    Utilityfuns.setUserTheme(scene);
                });
            }
        });

        // set initial theme
        Utilityfuns.setUserTheme(scene);
    }

    /**
     * Make the Stage Visible
     */
    public void setVisible(boolean visible) {
        // if it is hide action
        if(!visible && this.isShowing()) {
            this.hide();
            return;
        } else if(!visible) {
            return;
        }

        // define path
        var dPath = Paths.get(LOC, ".facsimile");

        // create dir
        dPath.toFile().mkdirs();

        // check the mapping
        if (!this.readyAuth()) {
            return;
        }

        // set visible or not
        if (visible && !this.isShowing()) {
            this.show();
        }
    }

    /**
     * set the Primary Stage
     * 
     * @param pStage Stage
     */
    public void setPrimaryStage(Stage pStage) {
        // init the pstage
        this.stageInit(pStage);

        // init the stage
        this.initOwner(pStage);
        this.initStyle(StageStyle.TRANSPARENT);
    }

    /**
     * Get the Facsimile Instance
     * 
     * @return instance
     */
    public static Facsimile getInstance() {
        if (instance == null) {
            instance = new Facsimile();
        }

        return instance;
    }
}
