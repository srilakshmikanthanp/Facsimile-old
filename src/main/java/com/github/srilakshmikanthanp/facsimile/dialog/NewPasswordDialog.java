// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.dialog;


import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;


/**
 * New password dialog to Create password
 */
public class NewPasswordDialog extends Stage
{
    // button status
    public static final int OK_BUTTON = 1, CALCEL_BUTTON = 2;

    // Password lable
    private Label newlabel = new Label("Enter Password");

    // Password text field
    private PasswordField newPass = new PasswordField();

    // Password lable
    private Label conlabel = new Label("Confirm Password");

    // Password text field
    private PasswordField conPass = new PasswordField();

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
        spane.getChildren().addAll(cutLabel, cutButton);
        mpane.setPadding(new Insets(5));
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
        // add it to pane
        var hbox = new HBox(okButton, cancelButton);
        var pane = new VBox();

        // add event listeners
        okButton.setOnAction((evt) -> {
            this.buttonPressed = OK_BUTTON;
            this.hide();
        });

        cancelButton.setOnAction((evt) -> {
            this.buttonPressed = CALCEL_BUTTON;
            this.hide();
        });

        // inti pane
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);

        // add controls
        pane.setSpacing(15);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10));
        pane.getChildren().addAll(
            newlabel,
            newPass,
            conlabel,
            conPass,
            hbox
        );

        // done
        return pane;
    }

    /**
     * Constructor
     * 
     * @param pStage primary stage
     * @param errorFree is error free
     */
    public NewPasswordDialog(Window parent, boolean errorFree)
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
        var scene = new Scene(pane, 300, 300);

        // set scene
        this.setScene(scene);
    }

    /**
     * Get old password
     * 
     * @return old password
     */
    public String getNewPassword()
    {
        return newPass.getText();
    }

    /**
     * Get new password
     * 
     * @return new password
     */
    public String getConPassword()
    {
        return conPass.getText();
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
