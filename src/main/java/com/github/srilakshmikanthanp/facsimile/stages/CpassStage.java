package com.github.srilakshmikanthanp.facsimile.stages;

import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;

/**
 * User Preference Stage
 */
public class CpassStage extends Stage
{
    // button status
    public static final int OK_BUTTON = 1, CALCEL_BUTTON = 2;

    // Title
    private Label facsimile = new Label("Facsimile");

    // Password lable
    private Label oldlabel = new Label("Old Password");

    // Password text field
    private PasswordField oldPass = new PasswordField();

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
        mpane.setLeft(facsimile);
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
        var pane = new GridPane();

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
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setAlignment(Pos.CENTER);

        // add controls
        pane.add(oldlabel, 0, 0);
        pane.add(oldPass, 1, 0);
        pane.add(newlabel, 0, 1);
        pane.add(newPass, 1, 1);
        pane.add(conlabel, 0, 2);
        pane.add(conPass, 1, 2);
        pane.add(hbox, 1, 3);
        pane.setPadding(new Insets(10));

        // done
        return pane;
    }

    /**
     * Constructor for Change Password
     * 
     * @param pStage primary stage
     * @param errorFree is error free
     */
    public CpassStage(Stage parent, boolean errorFree)
    {
        // init modality, style and parent
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.TRANSPARENT);
        this.initOwner(parent);

        // TODO style the Stage
        this.facsimile.setFont(new Font(15));

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
     * Get old password
     * 
     * @return old password
     */
    public String getOldPassword()
    {
        return oldPass.getText();
    }

    /**
     * Get new password
     * 
     * @return new password
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
