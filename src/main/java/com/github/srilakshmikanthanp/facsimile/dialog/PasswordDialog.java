package com.github.srilakshmikanthanp.facsimile.dialog;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;

/**
 * Top Bar of the application.
 */
public class PasswordDialog extends Dialog<String>
{
    // Password fied
    PasswordField passwordField = new PasswordField();

    /**
     * Constructor of password dialog
     * 
     * @param title title of dialog
     */
    public PasswordDialog(String context)
    {
        // set prompt text for dialog
        this.setTitle("Facsimile Password");
        this.setHeaderText(context);

        // set promt text for field
        this.passwordField.setPromptText("password");

        // add button types
        this.getDialogPane().getButtonTypes().addAll(
            ButtonType.OK,
            ButtonType.CANCEL
        );

        // define pane
        var hbox = new HBox(this.passwordField);

        // init pane
        hbox.setPadding(new Insets(10));
        HBox.setHgrow(passwordField, Priority.ALWAYS);

        // set content
        this.getDialogPane().setContent(hbox);

        // set converter
        this.setResultConverter((buttonType) -> {
            if(buttonType == ButtonType.OK) {
                return passwordField.getText();
            }
            return null;
        });
    }

    /**
     * Returns the Passworld Field
     */
    public PasswordField getPasswordField()
    {
        return this.passwordField;
    }
}
