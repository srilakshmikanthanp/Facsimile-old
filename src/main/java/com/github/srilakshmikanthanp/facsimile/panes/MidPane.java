package com.github.srilakshmikanthanp.facsimile.panes;


import java.security.GeneralSecurityException;
import java.util.ArrayList;

import javafx.scene.layout.*;
import javafx.util.Pair;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.utility.*;

/**
 * Application Middle Pane
 */
public class MidPane extends BorderPane
{
    // List of keys showed to user
    private ListView<String> listView = new ListView<>();

    // Mapping data for app
    private Mapping mapping;

    /**
     * Update the mapping data too user
     */
    public void updateMapping()
    {
        // define vars for map
        ArrayList<Pair<String, String>> pairs;

        // get mapping data
        try
        {
            pairs = this.mapping.getPairs();
        }
        catch(GeneralSecurityException e)
        {
            Utilityfunc.showError(e);
            return;
        }

        // clear list
        this.listView.getItems().clear();

        // add items to list
        for(var pair : pairs)
        {
            this.listView.getItems().add(
                pair.getKey()
            );
        }
    }

    /**
     * Copies the value of key to clipboard
     * 
     * @param key key to copy
     */
    private void copyClipAndHide()
    {
        // defne
        String valueOftheKey = null;

        // get
        try
        {
            valueOftheKey = mapping.get(
                listView.getSelectionModel()
                .getSelectedItem()
            );
        }
        catch(GeneralSecurityException e)
        {
            Utilityfunc.showError(e);
            return;
        }

        // copy
        var sysBoard = Clipboard.getSystemClipboard();
        var content = new ClipboardContent();
        content.putString(valueOftheKey);
        sysBoard.setContent(content);
    }

    /**
     * Constructor for MidPane
     * 
     * @param mapping map data
     */
    public MidPane(Mapping mapping)
    {
        // save map
        this.mapping = mapping;

        // initlize the pane
        this.setCenter(listView);
        this.setPadding(new Insets(15));

        // add key event
        this.listView.setOnKeyPressed((evt) -> {
            if(evt.getCode() == KeyCode.ENTER) {
                this.copyClipAndHide();
            } else if(evt.getCode() == KeyCode.DELETE) {
             //   this.deleteKey();
            }
        });

        // addlistener to map
        this.mapping.addChangeListener(() -> {
            this.updateMapping();
        });

        // Initial add
        this.sceneProperty().addListener((sobs, soldVal, scene) -> {
            scene.windowProperty().addListener((wobs, woldval, window) -> {
                window.setOnShowing((evt) -> {
                    this.updateMapping();
                });
            });
        });
    }
}
