package com.github.srilakshmikanthanp.facsimile.dialog;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * Key Value Input Dialog.
 */
public class KeyValueInput extends AbstractDialog {
    // Label for the Dialog
    private Label label = new Label("Key Value");

    // Key Field for prompt
    private TextField keyFd = new TextField();

    // Value Field for prompt
    private PasswordField valFd = new PasswordField();

    // is status okay for dialog
    private boolean isOkay = false;

    /**
     * Get the Content pane.
     */
    @Override
    protected Node getContent() {
        // init the Label
        var image = new ImageView(
            getClass().getResource(
                "/images/keyvalue.png"
            ).toString()
        );
        image.setFitWidth(100);
        image.setFitHeight(100);
        label.setGraphic(image);
        label.setContentDisplay(
            ContentDisplay.TOP
        );

        // init the key field
        keyFd.setPromptText("Key");
        keyFd.setOnKeyPressed((evt) -> {
            var code = evt.getCode();
            var enter = KeyCode.ENTER;
            if (code == enter) {
                valFd.requestFocus();
            }
        });

        // init the value field
        valFd.setPromptText("Value");
        valFd.setOnKeyPressed((evt) -> {
            var code = evt.getCode();
            var enter = KeyCode.ENTER;
            if (code == enter) {
                this.okayPressed();
            }
        });

        // define the vbox
        var vbox = new VBox(
            label,
            keyFd,
            valFd
        );

        // init the vbox
        vbox.setAlignment(
            Pos.CENTER
        );

        // done
        return vbox;
    }

    /**
     * Okay pressed Event.
     */
    @Override
    protected void okayPressed() {
        // set the status
        this.isOkay = true;

        // close the dialog
        this.hide();
    }

    /**
     * Cancel pressed Event.
     */
    @Override
    protected void cutPressed() {
        // set the status
        this.isOkay = false;

        // close the dialog
        this.hide();
    }

    /**
     * Constructor for Key Value Input Dialog.
     * 
     * @param owner owner
     */
    public KeyValueInput(Window owner) {
        super(owner);
    }

    /**
     * Is the status of dialog in okay
     * 
     * @return boolean status
     */
    public boolean isOkay() {
        return this.isOkay;
    }

    /**
     * Get the Key that user entered.
     * 
     * @return String
     */
    public String getKey() {
        return this.keyFd.getText();
    }

    /**
     * Get the Value that user entered.
     * 
     * @return String
     */
    public String getValue() {
        return this.valFd.getText();
    }
}
