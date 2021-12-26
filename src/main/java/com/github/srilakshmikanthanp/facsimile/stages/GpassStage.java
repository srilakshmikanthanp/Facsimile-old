// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.stages;


import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;


/**
 * Get password dialog to get password
 */
public class GpassStage extends Stage
{
    // button status
    public final int OK_BUTTON = 1, CALCEL_BUTTON = 2;

    // Password lable
    private Label passLabel = new Label("Password");

    // Password text field
    private PasswordField passField = new PasswordField();

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
        var mpane = new HBox();

        // add listeners
        cutButton.setOnAction((evt) ->{
            this.buttonPressed = CALCEL_BUTTON;
            this.hide();
        });

        cutButton.setOpacity(0);
        spane.getChildren().addAll(cutLabel, cutButton);
        mpane.setAlignment(Pos.CENTER_RIGHT);
        mpane.getChildren().add(spane);

        return mpane;
    }

    /**
     * Return the Center Pane
     * 
     * @return pane
     */
    private Pane getCenterPane()
    {
        // add it to pane
        var hbox = new HBox(passLabel, passField);
        var bbox = new HBox(okButton, cancelButton);
        var vbox = new VBox(hbox, bbox);

        // add event listeners
        okButton.setOnAction((evt) -> {
            this.buttonPressed = OK_BUTTON;
            this.hide();
        });
  
        cancelButton.setOnAction((evt) -> {
            this.buttonPressed = CALCEL_BUTTON;
            this.hide();
        });

        hbox.setSpacing(10);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        // done
        return vbox;
    }

    /**
     * Constructor
     * 
     * @param pStage primary stage
     * @param errorFree is error free
     */
    public GpassStage(Stage parent, boolean errorFree)
    {
        // init modality, style and parent
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.TRANSPARENT);
        this.initOwner(parent);

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
    }

    /**
     * Get Password
     */
    public String getPassword()
    {
        return this.passField.getText();
    }

    /**
     * get the pressed button
     * 
     * @return state
     */
    public int getButtonPressed()
    {
        return this.buttonPressed;
    }
}
