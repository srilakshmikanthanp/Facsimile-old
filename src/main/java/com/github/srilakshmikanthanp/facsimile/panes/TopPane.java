package com.github.srilakshmikanthanp.facsimile.panes;

import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.dialog.Password;

/**
 * Top Section of the Pane
 */
public class TopPane extends BorderPane
{
    // Mapping data
    private Mapping mapping;

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
     * Change the ShortCut
     */
    private void ChangeShortCut()
    {
        // TODO : Change the ShortCut
    }

    /**
     * Change the Password
     */
    private void ChangePassword()
    {
        // create dialog
        var dialog = new Password (
            this.getScene().getWindow(),
            Password.CHANGE_PASSWORD
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
            this.showError(e.getMessage());
            return;
        }
    }

    /**
     * Get ShortCut Change Button
     */
    private Button getShortCutButton()
    {
        // define button
        var button = new Button();

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
        button.setGraphic(image);

        // set the action
        button.setOnAction(evt -> {
            this.ChangeShortCut();
        });

        // done
        return button;
    }

    /**
     * Get Password Change Button
     */
    private Button getPasswordButton()
    {
        // define button
        var button = new Button();

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
        button.setGraphic(image);

        // set the action
        button.setOnAction(evt -> {
            this.ChangePassword();
        });

        // done
        return button;
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
        this.setLeft(this.getShortCutButton());
        this.setRight(this.getPasswordButton());
    }
}
