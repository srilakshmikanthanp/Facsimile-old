package com.github.srilakshmikanthanp.facsimile;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.dialog.*;
import com.github.srilakshmikanthanp.facsimile.utility.*;

/**
 * The top pane foe the application.
 */
class TopPane extends BorderPane {
    // Crypto Hash Map
    private CryptoMap cryptoMap;

    /**
     * Change the ShortCut
     */
    private void ChangeShortCut() {
        // create dialog
        var dialog = new InputShortCut(
            this.getScene().getWindow()
        );

        // show dialog
        dialog.showAndWait();

        // if not ok
        if (!dialog.isOkay()) {
            return;
        }

        // get the data
        var masksOne = dialog.getMaskOne();
        var masksTwo = dialog.getMaskTwo();
        var keyValue = dialog.getKeycode();

        // set the ShortCut
        Preference.setMaskOne(masksOne);
        Preference.setMaskTwo(masksTwo);
        Preference.setKeyValue(keyValue);
    }

    /**
     * Change the Password
     */
    private void ChangePassword() {
        // create dialog
        var dialog = new AlterPassword(
            this.getScene().getWindow()
        );

        // loop while the user does't enter valid password
        while(true) {
            // show dialog to the user for input
            dialog.showAndWait();

            // if not ok or user presserd cancel
            if (!dialog.isOkay()) {
                return;
            }

            // try to change the password
            try {
                var oldPass = dialog.getOldPassword();
                var newPass = dialog.getNewPassword();
                cryptoMap.changePassword(oldPass, newPass);
                return;
            } catch (IOException e) {
                dialog.setLabel("Invalid Auth");
                dialog.setError(true);
            } catch (GeneralSecurityException e) {
                Utilityfuns.showError(e);
                return;
            }
        }
    }

    /**
     * Get the short cut node
     * 
     * @return Node
     */
    private Node getShortcutNode() {
       // define button
       var button = new Button();
       var blabel = new Label();

       // image view
       var image = new ImageView(
           getClass().getResource(
               "/images/shortcut.png"
           ).toString()
       );

       // init the image
       image.setFitHeight(30);
       image.setFitWidth(30);

       // init button
       button.setOpacity(0.0);
       blabel.setGraphic(image);

       // set the action
       button.setOnAction(evt -> {
           this.ChangeShortCut();
       });

       // done
       return new StackPane(
           blabel, button
       );
    }

    /**
     * Get the password node
     * 
     * @return Node
     */
    private Node getPasswordNode() {
       // define button
       var button = new Button();
       var blabel = new Label();

       // image view
       var image = new ImageView(
           getClass().getResource(
               "/images/password.png"
           ).toString()
       );

       // init the image
       image.setFitHeight(30);
       image.setFitWidth(30);

       // init button
       button.setOpacity(0.0);
       blabel.setGraphic(image);

       // set the action
       button.setOnAction(evt -> {
           this.ChangePassword();
       });

       // done
       return new StackPane(
           blabel, button
       );
    }

    /**
     * Constructor for the TopPane.
     * 
     * @param map CryptoMap.
     */
    public TopPane(CryptoMap map) {
        //  save crypto map
        this.cryptoMap = map;

        // set the nodes
        this.setLeft(this.getShortcutNode());
        this.setRight(this.getPasswordNode());
    }
}

/**
 * The top pane foe the application.
 */
class MidPane extends BorderPane {
    // Crypto Hash Map
    private CryptoMap cryptoMap;

    /**
     * Constructor for the TopPane.
     * 
     * @param map CryptoMap.
     */
    public MidPane(CryptoMap map) {
        //  save crypto map
        this.cryptoMap = map;
    }
}

/**
 * The top pane foe the application.
 */
class BotPane extends BorderPane {
    // Crypto Hash Map
    private CryptoMap cryptoMap;

    /**
     * Constructor for the TopPane.
     * 
     * @param map CryptoMap.
     */
    public BotPane(CryptoMap map) {
        //  save crypto map
        this.cryptoMap = map;
    }
}

/**
 * The main application pane. A Wrapper
 * Over Other Three Panes
 */
public class AppPane extends BorderPane {
    /**
     * Constructor for the AppPane.
     * 
     * @param map CryptoMap.
     */
    public AppPane(CryptoMap map) {
        //  create panes
        var topPane = new TopPane(map);
        var midPane = new MidPane(map);
        var botPane = new BotPane(map);

        //  set the panes
        this.setTop(topPane);
        this.setCenter(midPane);
        this.setBottom(botPane);

        //  init the pane
        this.setPadding(new Insets(10));
    }
}
