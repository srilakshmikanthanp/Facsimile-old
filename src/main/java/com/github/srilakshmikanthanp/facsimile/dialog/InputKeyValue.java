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
public class InputKeyValue extends AbstractDialog {
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
            getClass().getResource("/images/keyvalue.png").toString()
        );
        image.setFitWidth(100);
        image.setFitHeight(100);
        label.setGraphic(image);
        label.setContentDisplay(ContentDisplay.TOP);

        // init the key field
        keyFd.setPromptText("Key");
        keyFd.setOnKeyPressed((evt) -> {
            if (evt.getCode() == KeyCode.ENTER) {
                valFd.requestFocus();
            }
        });

        // init the value field
        valFd.setPromptText("Value");
        valFd.setOnKeyPressed((evt) -> {
            if (evt.getCode() == KeyCode.ENTER) {
                this.okayPressed();
            }
        });

        // define the vbox
        var vbox = new VBox(label, keyFd, valFd);

        // init the vbox
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10.0));

        // done
        return vbox;
    }

    /**
     * Okay pressed Event.
     */
    @Override
    protected void okayPressed() {
        // check if key is not empty
        if (!keyFd.getText().isEmpty()) {
            // set the status
            this.isOkay = true;

            // close the dialog
            this.hide();
        } else {
            // set error
            this.setError(true);

            // focus
            keyFd.requestFocus();
        }
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
    public InputKeyValue(Window owner) {
        super(owner);
    }

    /**
     * Set's the dialog Status as Error.
     * 
     * @param isError Status
     */
    public void setError(boolean isError) {
        if (isError) {
            this.label.setStyle("-fx-text-fill: red;");
        } else {
            this.label.setStyle("");
        }
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
