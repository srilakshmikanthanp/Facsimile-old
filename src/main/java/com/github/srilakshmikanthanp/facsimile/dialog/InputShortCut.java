package com.github.srilakshmikanthanp.facsimile.dialog;

import java.util.Arrays;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import com.github.srilakshmikanthanp.facsimile.consts.*;
import com.github.srilakshmikanthanp.facsimile.utility.Preference;


/**
 * PAssword Input Dialog.
 */
public class InputShortCut extends AbstractDialog {
    // Mask one of short cut
    private ComboBox<String> maskOne = new ComboBox<>();

    // Mask two of short cut
    private ComboBox<String> maskTwo = new ComboBox<>();

    // Key code of short cut
    private ComboBox<String> keycode = new ComboBox<>();

    // label for theShort
    private Label label = new Label("Change Shortcut");

    // status of the dialog
    private boolean isOkay = false;

    /**
     * Get the Content pane.
     */
    private Node getContent() {
        // init the label
        var image = new ImageView(
            getClass().getResource("/images/shortcut.png").toString()
        );
        image.setFitHeight(80);
        image.setFitWidth(80);
        label.setGraphic(image);
        label.setGraphicTextGap(20);
        label.setContentDisplay(ContentDisplay.TOP);

        // init the mask one
        maskOne.getItems().addAll(
            AppConsts.masks
        );

        // set the value of mask one
        maskOne.setValue(
            Preference.getMaskOne()
        );

        // init the mask two
        maskTwo.getItems().addAll(
            AppConsts.masks
        );

        // set the value of mask two
        maskTwo.setValue(
            Preference.getMaskTwo()
        );

        // init the keycode
        var list = Arrays.asList(
            AppConsts.keys
        );

        // Sort the list
        list.sort((a, b) -> a.compareTo(b));
        
        // remove the Masks
        list.removeAll(Arrays.asList(AppConsts.masks));

        // add the keycode
        keycode.getItems().addAll(
            list
        );

        // set the value of keycode
        keycode.setValue(
            Preference.getKeyValue()
        );

        // set max row count
        keycode.setVisibleRowCount(3);

        // define the vbox
        var vbox = new VBox(label, maskOne, maskTwo, keycode);

        // init the vbox
        vbox.setSpacing(15);
        vbox.setPadding(new Insets(20));
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
        this.setContent(getContent());
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

    /**
     * Get the keycode.
     */
    public String getKeycode() {
        return this.keycode.getValue();
    }
}
