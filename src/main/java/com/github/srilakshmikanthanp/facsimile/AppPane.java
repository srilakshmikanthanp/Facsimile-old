package com.github.srilakshmikanthanp.facsimile;

import java.io.IOException;
import java.util.function.BooleanSupplier;
import java.security.GeneralSecurityException;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import org.controlsfx.control.ToggleSwitch;

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

        // show dialog and status
        BooleanSupplier isOkay = () -> {
            dialog.showAndWait();
            return dialog.isOkay();
        };

        // while the password is't valid
        while(!isOkay.getAsBoolean()) {
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
    // List of keys showed to user
    private ListView<String> listView = new ListView<>();

    // Crypto Hash Map
    private CryptoMap cryptoMap;

    /**
     * Map change Updater.
     */
    private void mapChangeUpdater(int type, String key, String oldVal, String newVal) {
        // try to save to file
        try {
            this.cryptoMap.saveJson();
        } catch (IOException | GeneralSecurityException e) {
            this.cryptoMap.put(key, oldVal);
            Utilityfuns.showError(e);
            return;
        }

        // Clear the listView
        listView.getItems().clear();

        // set the items
        cryptoMap.keySet().forEach((k) -> {
            listView.getItems().add(k);
        });
    }

    /**
     * Copy the selected item to clipboard
     */
    private void copySelectedItem() {
        // get the selected item
        var model = listView.getSelectionModel();
        var selectedItem = model.getSelectedItem();

        // if no item selected
        if (selectedItem == null) {
            return;
        }

        // get the data
        var data = cryptoMap.get(selectedItem);

        // copy the data
        Utilityfuns.copyToClipboard(data);
        this.getScene().getWindow().hide();
    }

    /**
     * Delete the selected item
     */
    private void deleteSelectedItem() {
        // get the selected item
        var model = listView.getSelectionModel();
        var selectedItem = model.getSelectedItem();

        // if no item selected
        if (selectedItem == null) {
            return;
        }

        // delete the item
        cryptoMap.remove(selectedItem);
    }

    /**
     * Key press Event Handler for ListView.
     * 
     * @param code KeyCode.
     */
    private void listViewKeyPress(KeyCode code) {
        if(code == KeyCode.ENTER) {
            this.copySelectedItem();
        } else if(code == KeyCode.DELETE) {
            this.deleteSelectedItem();
        }
    }

    /**
     * Constructor for the TopPane.
     * 
     * @param map CryptoMap.
     */
    public MidPane(CryptoMap map) {
        //  save crypto map
        this.cryptoMap = map;

        // set the nodes
        this.setCenter(listView);

        // set the map updater
        cryptoMap.addMapChangeListener(
            this::mapChangeUpdater
        );

        // set the action
        listView.setOnKeyPressed(evt -> {
            this.listViewKeyPress(
                evt.getCode()
            );
        });

        // set the action
        listView.setOnMouseClicked(evt -> {
            if(evt.getClickCount() == 2) {
                this.copySelectedItem();
            }
        });

        // initial update
        cryptoMap.keySet().forEach((k) -> {
            listView.getItems().add(k);
        });
    }
}

/**
 * The top pane foe the application.
 */
class BotPane extends BorderPane {
    // Crypto Hash Map
    private CryptoMap cryptoMap;

    /**
     * The theme was changed
     * 
     * @param isDark
     */
    private void themeChanged(boolean isDark) {
        Preference.setDarkMode(isDark);
    }

    /**
     * Adds the key value Pair
     */
    private void addKeyValuePair() {
        // create dialog
        var dialog = new InputKeyValue(
            this.getScene().getWindow()
        );

        // show dialog
        dialog.showAndWait();

        // if not okay
        if(!dialog.isOkay()) {
            return;
        }

        // get the key and value
        var key = dialog.getKey();
        var value = dialog.getValue();

        // add the key value pair
        cryptoMap.put(key, value);
    }

    /**
     * Get the theme Node
     */
    private Node getThemeNode() {
        // create toggle
        var toggle = new ToggleSwitch();

        // set the theme
        toggle.setSelected(
            Preference.getDarkMode()
        );

        // add listener
        toggle.selectedProperty().addListener(
            (observable, oldValue, newValue) -> {
                this.themeChanged(newValue);
            }
        );

        // done
        return toggle;
    }

    /**
     * Get the item adder
     */
    private Node getAdderNode() {
        // define button
        var button = new Button();
        var blabel = new Label();

        // image view
        var image = new ImageView(
            getClass().getResource(
                "/images/plus.png"
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
            this.addKeyValuePair();
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
    public BotPane(CryptoMap map) {
        //  save crypto map
        this.cryptoMap = map;

        // set the nodes
        this.setLeft(this.getThemeNode());
        this.setRight(this.getAdderNode());
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
        var botPane = new MidPane(map);

        //  set the panes
        this.setTop(topPane);
        this.setCenter(midPane);
        this.setBottom(botPane);

        //  init the pane
        this.setPadding(new Insets(10));
    }
}
