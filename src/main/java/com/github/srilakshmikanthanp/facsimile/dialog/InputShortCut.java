package com.github.srilakshmikanthanp.facsimile.dialog;

import java.util.Arrays;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;


/**
 * PAssword Input Dialog.
 */
public class InputShortCut extends AbstractDialog {
    // Mask one of short cut
    private ChoiceBox<String> maskOne = new ChoiceBox<>();

    // Mask two of short cut
    private ChoiceBox<String> maskTwo = new ChoiceBox<>();

    // Key code of short cut
    private ChoiceBox<String> keycode = new ChoiceBox<>();

    // label for theShort
    private Label label = new Label("Shortcut");

    // status of the dialog
    private boolean isOkay = false;

    /**
     * Get the Content pane.
     */
    @Override
    protected Node getContent() {
        // init the label
        var image = new ImageView(
            getClass().getResource("/images/shortcut.png").toString()
        );
        image.setFitHeight(100);
        image.setFitWidth(100);
        label.setGraphic(image);
        label.setContentDisplay(ContentDisplay.TOP);

        // init the mask one
        maskOne.getItems().addAll(
            // TODO: add the mask one
        );

        // init the mask two
        maskTwo.getItems().addAll(
            // TODO: add the mask two
        );

        // init the keycode
        var list = Arrays.asList(
            KeyCode.values()
        ).stream().map(
            (key) -> key.getName().toUpperCase()
        ).collect(
            java.util.stream.Collectors.toList()
        );

        // Sort the list
        list.sort((a, b) -> a.compareTo(b));
        
        // TODO: remove the keycode

        // add the keycode
        keycode.getItems().addAll(
            list
        );

        // define the vbox
        var vbox = new VBox(label, maskOne, maskTwo, keycode);

        // init the vbox
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        // done
        return vbox;
    }

    /**
     * Okay pressed Event.
     */
    @Override
    protected void okayPressed() {
        this.isOkay = true;
        this.hide();
    }

    /**
     * Cancel pressed Event.
     */
    @Override
    protected void cutPressed() {
        this.isOkay = false;
        this.hide();
    }

    /**
     * Constructor for Password Input Dialog.
     * 
     * @param owner owner
     */
    public InputShortCut(Window owner) {
        super(owner);
    }

    /**
     * Get the status of the dialog.
     */
    public boolean isOkay() {
        return this.isOkay;
    }

    /**
     * Get the mask one.
     */
    public String getMaskOne() {
        return this.maskOne.getValue();
    }

    /**
     * Get the mask two.
     */
    public String getMaskTwo() {
        return this.maskTwo.getValue();
    }
}
