// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.panes;

import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;


/**
 * New password dialog to Create password
 */
class NewPasswordDialog extends Stage
{
    // Password lable
    private Label oldlabel = new Label("Old Password");

    // Password text field
    private PasswordField oldPass = new PasswordField();

    // Password lable
    private Label newlabel = new Label("New Password");

    // Password text field
    private PasswordField newPass = new PasswordField();

    // Okay Button
    private Button okButton = new Button("OK");

    // calcel button
    private Button cancelButton = new Button("Cancel");

    /**
     * Return the Top Pane
     * 
     * @return pane
     */
    private Pane getTopPane()
    {
        var label = new Label("❌");
        var button = new Button();
        var spane = new StackPane();
        var mpane = new HBox();

        button.setOpacity(0);
        spane.getChildren().addAll(label, button);
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
        var hbox = new HBox(okButton, cancelButton);
        var pane = new GridPane();

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
        pane.add(hbox, 1, 2);
        pane.setPadding(new Insets(10));

        // done
        return pane;
    }

    /**
     * Constructor
     */
    public NewPasswordDialog(Stage parent)
    {
        // init modality, style and parent
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.TRANSPARENT);
        this.initOwner(parent);

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
}

/**
 * Get password dialog to get password
 */
class GetPasswordDialog extends Stage
{
    // Password lable
    private Label label = new Label("Password");

    // Password text field
    private PasswordField pass = new PasswordField();

    // Okay Button
    private Button okButton = new Button("OK");

    /**
     * Return the Top Pane
     * 
     * @return pane
     */
    private Pane getTopPane()
    {
        var label = new Label("❌");
        var button = new Button();
        var spane = new StackPane();
        var mpane = new HBox();

        button.setOpacity(0);
        spane.getChildren().addAll(label, button);
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
        var hbox = new HBox(label, pass);
        var vbox = new VBox(hbox, okButton);

        hbox.setSpacing(10);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        // done
        return vbox;
    }

    /**
     * Constructor
     */
    public GetPasswordDialog(Stage parent)
    {
        // init modality, style and parent
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.TRANSPARENT);
        this.initOwner(parent);

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
        return this.pass.getText();
    }
}

/**
 * Main Pane for Application.
 */
public class MainPane extends BorderPane
{
    public MainPane(Stage pStage)
    {

    }
}
