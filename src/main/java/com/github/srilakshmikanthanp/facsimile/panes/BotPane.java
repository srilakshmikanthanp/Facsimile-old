package com.github.srilakshmikanthanp.facsimile.panes;


import java.io.IOException;
import java.security.GeneralSecurityException;

import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.control.*;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.dialog.*;
import com.github.srilakshmikanthanp.facsimile.utility.Utilityfunc;

/**
 * A pane that displays a option to add key-value pair.
 */
public class BotPane extends BorderPane
{
    // Mapping Data
    private Mapping mapping;

    /**
     * Add a key-value pair to the mapping.
     */
    private void addKeyValuePair()
    {
        // create dialog
        var dialog = new KeyValueDialog(
            this.getScene().getWindow()
        );

        // show dialog
        dialog.showAndWait();

        // if not ok
        if(!dialog.isOk())
        {
            return;
        }

        // get the values
        var key = dialog.getKey();
        var value = dialog.getValue();

        // add the key-value pair
        try 
        {
            this.mapping.put(key, value);
        } 
        catch (IOException | GeneralSecurityException e) 
        {
            Utilityfunc.showError(e);
        }
    }

    /**
     * Create the pane with plus button.
     * 
     * @return pane
     */
    private Node getPlusButton()
    {
        // define vars
        var plsbtn  = new Button();

        // init button
        var imagev = new ImageView(
            getClass().getResource(
                "/images/plus.png"
            ).toString()
        );
        imagev.setFitWidth(20);
        imagev.setFitHeight(20);
        plsbtn.setGraphic(imagev);
        plsbtn.setContentDisplay(
            ContentDisplay.TOP
        );
        plsbtn.setMaxWidth(
            Double.MAX_VALUE
        );
        plsbtn.setOnAction((evt) -> {
            addKeyValuePair();
        });

        // return
        return plsbtn;
    }
 
    /**
     * Constructs a new bot pane.
     * 
     * @param mapping map data
     */
    public BotPane(Mapping mapping)
    {
        // get the Mapping Pane
        this.mapping = mapping;

        // add the plus button
        this.setCenter(this.getPlusButton());
    }
}
