package com.github.srilakshmikanthanp.facsimile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.function.BooleanSupplier;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;

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
    private static final String LOC = DEV_LOC;

    // password input dialog for auth
    private static final InputPassword pwdDialog = new InputPassword(null);

    // password create dialog for auth
    private static final MakePassword mkPwdDialog = new MakePassword(null);

    // Crypto Hash Mapping
    private final CryptoMap cryptoMap = new CryptoMap(Path.of(LOC));

    /**
     * Create password for user
     */
    private boolean makePassword() {
        // if aldredy showing
        if (mkPwdDialog.isShowing()) {
            mkPwdDialog.toFront();
            return false;
        }

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
        while (!isOkay.getAsBoolean()) {
            try {
                var pass = pwdDialog.getPassword();
                cryptoMap.loadCrypto(pass);
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
        return true;
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
    private void initPStage(Stage pStage) {
        pStage.initStyle(StageStyle.UTILITY);
        pStage.setMaxHeight(0);
        pStage.setMaxWidth(0);
        pStage.setOpacity(0);
        pStage.setX(Double.MAX_VALUE);
    }

    /**
     * Constructor For Facsimile.
     * 
     * @param pStage Stage
     */
    public Facsimile(Stage pStage) {
        // init the pstage
        this.initPStage(pStage);

        // init the stage
        this.initOwner(pStage);
        this.initStyle(StageStyle.TRANSPARENT);

        // app pane
        var pane = new AppPane(cryptoMap);
        pane.getStyleClass().add("main-pane");
        var stkPane = new StackPane(pane);
        stkPane.setPadding(new Insets(10));
        stkPane.getStyleClass().add("container");

        // set ecene for stage
        this.setScene(new Scene(stkPane));

        // init the stage
        this.setAlwaysOnTop(true);
        this.setOnShown((evt) -> {
            this.setHeight(Stageheight);
            this.setMinWidth(Stagewidth);
            Utilityfuns.centerToScreen(this);
        });
    }

    /**
     * Make the Stage Visible
     */
    public void setVisible(boolean visible) {
        // define path
        var dPath = Paths.get(LOC, ".facsimile");

        // create dir
        dPath.toFile().mkdirs();

        // check the mapping
        if (!this.readyAuth()) {
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
