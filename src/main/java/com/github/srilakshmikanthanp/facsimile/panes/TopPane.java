package com.github.srilakshmikanthanp.facsimile.panes;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.dialog.*;
import com.github.srilakshmikanthanp.facsimile.utility.*;

/**
 * Top Section of the Pane
 */
public class TopPane extends BorderPane
{
    // Mapping data
    private Mapping mapping;

    /**
     * Change the ShortCut
     */
    private void ChangeShortCut()
    {
        // create dialog
        var dialog = new ShortCutDialog(
            this.getScene().getWindow()
        );

        // show dialog
        dialog.showAndWait();

        // if not ok
        if(!dialog.isOk())
        {
            return;
        }

        // get the data
        var masksOne = dialog.getMaskOne();
        var masksTwo = dialog.getMaskTwo();
        var keyValue = dialog.getKey();

        // set the ShortCut
        Preference.setMaskOne(masksOne);
        Preference.setMaskTwo(masksTwo);
        Preference.setKeyValue(keyValue);
    }

    /**
     * Change the Password
     */
    private void ChangePassword()
    {
        // create dialog
        var dialog = new PassWordDialog (
            this.getScene().getWindow()
        );

        // set Type of dialog
        dialog.setType(
            PassWordDialog.CHANGE_PASSWORD
        );

        //show the doalog
        dialog.showAndWait();

        // if not valid
        if(!dialog.isOkay())
        {
            return;
        }

        // try to change password
        try 
        {
            mapping.getCryptoEn().changeKeyPassword(
                dialog.getOldPassword(), 
                dialog.getActPassword()
            );
        } 
        catch (IOException | GeneralSecurityException e) 
        {
            Utilityfunc.showError(e);
            return;
        }
    }

    /**
     * Get ShortCut Change Button
     */
    private Pane getShortCutButton()
    {
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
        image.setFitHeight(20);
        image.setFitWidth(20);

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
     * Get Password Change Button
     */
    private Pane getPasswordButton()
    {
        // define button
        var button = new Button();
        var blabel = new Label();

        // image view
        var image = new ImageView(
            getClass().getResource(
                "/images/settings.png"
            ).toString()
        );

        // init the image
        image.setFitHeight(20);
        image.setFitWidth(20);

        // init button
        button.setOpacity(0.0);
        blabel.setGraphic(image);

        // set the action
        button.setOnAction(evt -> {
            this.ChangePassword();
        });

        // done
        // done
        return new StackPane(
            blabel, button
        );
    }

    /**
     * Constructor for top section pane
     * 
     * @param mapping mapping
     */
    public TopPane(Mapping mapping)
    {
        // save map
        this.mapping = mapping;

        // init tha pane
        this.setLeft(
            this.getShortCutButton()
        );
        this.setRight(
            this.getPasswordButton()
        );
    }
}
