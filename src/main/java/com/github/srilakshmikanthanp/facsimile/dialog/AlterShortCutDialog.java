// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.dialog;

import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.collections.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import com.github.srilakshmikanthanp.facsimile.system.*;
import com.github.srilakshmikanthanp.facsimile.utility.*;

/**
 * This class is used to Change ShortCut Key..
 */
public class AlterShortCutDialog extends Stage 
{
    // button status
    public static final int OK_BUTTON = 1, CALCEL_BUTTON = 2;

    // mask one box
    private ChoiceBox<String> maskOneBox = new ChoiceBox<> (
        FXCollections.observableArrayList(
            ShortCut.getKeys()
        )
    );

    // mask two box
    private ChoiceBox<String> maskTwoBox = new ChoiceBox<> (
        FXCollections.observableArrayList(
            ShortCut.getKeys()
        )
    );

    // ShortCut Key lable
    private Label nscutLabel = new Label("Shortcut Key");

    // TextField
    private TextField keyField = new TextField();

    // cut label
    private Label cutLabel = new Label("❌");

    // cut button
    private Button cutButton = new Button();

    // Okay Button
    private Button okButton = new Button("OK");

    // calcel button
    private Button cancelButton = new Button("Cancel");

    // current status of button
    private int buttonPressed = CALCEL_BUTTON;

    /**
     * Return the Top Pane
     * 
     * @return pane
     */
    private Pane getTopPane()
    {
        var spane = new StackPane();
        var mpane = new BorderPane();

        // add listeners
        cutButton.setOnAction((evt) ->{
            this.buttonPressed = CALCEL_BUTTON;
            this.hide();
        });

        cutButton.setOpacity(0);
        spane.getChildren().addAll(
            cutLabel, cutButton
        );
        mpane.setRight(spane);

        return mpane;
    }

    /**
     * Return the Center Pane
     * 
     * @return pane
     */
    private Pane getCenterPane()
    {
        var cbox = new HBox(maskOneBox, maskTwoBox, keyField);
        var bbox = new HBox(okButton, cancelButton);
        var vbox = new VBox(nscutLabel, cbox, bbox);

        // add event listeners
        okButton.setOnAction((evt) -> {
            this.buttonPressed = OK_BUTTON;
            this.hide();
        });

        cancelButton.setOnAction((evt) -> {
            this.buttonPressed = CALCEL_BUTTON;
            this.hide();
        });

        // intialize
        cbox.setSpacing(10);
        bbox.setSpacing(10);
        vbox.setSpacing(10);
        cbox.setAlignment(Pos.CENTER);
        bbox.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);

        // done
        return vbox;
    }

    /**
     * Constructor for NscutStage.
     * 
     * @param parent Parent Stage.
     * @param errorFree Flag to indicate if the stage is in error free mode.
     */
    public AlterShortCutDialog(Window parent, boolean errorFree) 
    {
        // init modality, style and parent
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.TRANSPARENT);
        this.initOwner(parent);

        // select default
        this.maskOneBox.getSelectionModel().select(
            Preference.getMaskOne()
        );
        this.maskTwoBox.getSelectionModel().select(
            Preference.getMaskTwo()
        );
        this.keyField.setText(
            Preference.getKeyValue()
        );

        // TODO style the Stage

        // add error color
        if(!errorFree)
        {
            // TODO add css class
        }

        // pane
        var pane = new BorderPane();

        // init
        pane.setTop(this.getTopPane());
        pane.setCenter(this.getCenterPane());
        pane.setPadding(new Insets(5, 10, 10, 5));

        // scene
        var scene = new Scene(pane);

        // set scene
        this.setScene(scene);

        // Limit the Length of Text Field to one
        this.keyField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                if(newValue.length() > 1) {
                    this.keyField.setText(newValue.substring(0, 1));
                }
            }
        );
    }

    /**
     * Return the Mask One Key.
     * 
     * @return Key
     */
    public String getMaskOne()
    {
        return this.maskOneBox.getValue();
    }

    /**
     * Return the Mask Two Key.
     * 
     * @return Key
     */
    public String getMaskTwo()
    {
        return this.maskTwoBox.getValue();
    }

    /**
     * Return the Text.
     * 
     * @return Text
     */
    public String getText()
    {
        return this.keyField.getText();
    }

    /**
     * Return the status of the button.
     * 
     * @return status
     */
    public int getButtonPressed()
    {
        return this.buttonPressed;
    }
}
