package com.github.srilakshmikanthanp.facsimile.panes;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import javafx.scene.layout.*;
import javafx.stage.Window;
import javafx.util.Pair;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

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
    private void updateListView()
    {
        // define vars for map
        ArrayList<Pair<String, String>> pairs;

        // get mapping data from map
        try
        {
            pairs = mapping.getPairs();
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
     * Gets the Selected Value from map
     * 
     * @return selected value
     */
    private String getSelectedValue()
    {
        var model = listView.getSelectionModel();
        var key = model.getSelectedItem();
        String value = null;

        // get value from map
        try
        {
            value = mapping.get(key);
        }
        catch(GeneralSecurityException e)
        {
            Utilityfunc.showError(e);
        }

        // Done
        return value;
    }

    /**
     * Copy the selected item to clip board
     */
    private void copyToClipboard()
    {
        var value = this.getSelectedValue();

        // check if value is null
        if(value == null)
        {
            return;
        }

        // create clipboard content
        var content = new ClipboardContent();
        content.putString(value);

        // copy to clipboard
        var sysBoard = Clipboard.getSystemClipboard();
        sysBoard.setContent(content);
    }

    /**
     * Delete the selected item from map
     */
    private void deleteSelected()
    {
        // define vars for map
        var model = listView.getSelectionModel();
        var key = model.getSelectedItem();

        // check if key is null
        if(key == null)
        {
            return;
        }

        // delete from map
        try
        {
            mapping.remove(key);
        }
        catch(IOException | GeneralSecurityException e)
        {
            Utilityfunc.showError(e);
        }

        // update map
        this.updateListView();
    }

    /**
     * Action Listener for the list view
     */
    private void listViewKeyEvent(KeyCode code)
    {
        // check if key is delete
        if(code == KeyCode.DELETE)
        {
            this.deleteSelected();
            return;
        }
        
        // check if key is delete
        if(code == KeyCode.ENTER)
        {
            this.copyToClipboard();
            this.getScene().getWindow().hide();
            return;
        }
    }

    /**
     * Constructor for the middle pane
     * 
     * @param mapping mapping
     */
    public MidPane(Mapping mapping)
    {
        // save map
        this.mapping = mapping;

        // set the list view
        this.setCenter(listView);
        this.setPadding(new Insets(15));

        // listeners to list view
        listView.setOnKeyPressed(evt -> {
            this.listViewKeyEvent(
                evt.getCode()
            );
        });

        // On Key Pressed
        listView.setOnMouseClicked((evt) -> {
            if(evt.getButton() == MouseButton.PRIMARY) {
                this.copyToClipboard();
            }
        });

        // add listener to map
        this.mapping.addChangeListener(() -> {
            this.updateListView();
        });

        // Window Listener for pane
        ChangeListener<Window> winLis = 
        ( obs, oldWin, newWin ) -> {
            newWin.setOnShowing((evt) -> {
                this.updateListView();
            });
        };
        
        // Scene Listener
        ChangeListener<Scene> sceneLis = 
        ( obs, oldScene, newScene ) -> {
            newScene.windowProperty().addListener(
                winLis
            );
        };

        // add listener to scene
        this.sceneProperty().addListener(
            sceneLis
        );
    }
}
