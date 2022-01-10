package com.github.srilakshmikanthanp.facsimile.dialog;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

/**
 * Password Alter (Change) Dialog.
 */
public class MakePassword extends AbstractDialog {
    // new password
    private PasswordField newPassword = new PasswordField();

    // confirm password
    private PasswordField conPassword = new PasswordField();

    // Label for the Dialog
    private Label label = new Label("Create Password");

    // dialog status
    private boolean isOkay = false;

    // min password length
    private int minLength = 8;

    /**
     * Get the Content pane.
     */
    private Node getContent() {
        // initlize the label
        var image = new ImageView(
            getClass().getResource("/images/user.png").toString()
        );
        label.setGraphic(image);
        label.setGraphicTextGap(10);
        label.setContentDisplay(ContentDisplay.TOP);

        // initilize the new password field
        newPassword.setOnKeyPressed((evt) -> {
            if (evt.getCode() == KeyCode.ENTER) {
                conPassword.requestFocus();
            }
        });

        // initilize the confirm password field
        conPassword.setOnKeyPressed((evt) -> {
            if (evt.getCode() == KeyCode.ENTER) {
                this.okayPressed();
            }
        });

        // define the VBox
        var vbox = new VBox(
            label, newPassword, conPassword
        );

        // init the VBox
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
        // get the old password and new password
        var newPass = newPassword.getText();
        var conPass = conPassword.getText();

        // check if the new password and confirm password are same
        if (!newPass.equals(conPass)) {
            this.setError(true);
            this.conPassword.requestFocus();
            return;
        }
    
        // chack if the password is valid
        if(newPass.length() < minLength) {
            this.setError(true);
            this.newPassword.requestFocus();
            return;
        }

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
        this.isOkay = false;
        this.hide();
    }

    /**
     * Constructor for Password Input Dialog.
     * 
     * @param owner owner
     */
    public MakePassword(Window owner) {
        super(owner);
        this.setContent(getContent());
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
     * Sets the Label text of Dialog.
     * 
     * @param title string
     */
    public void setLabel(String title) {
        this.label.setText(title);
    }

    /**
     * Set the minimum password length.
     * 
     * @return Password
     */
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    /**
     * Get the Dialog Status.
     * 
     * @return Status
     */
    public boolean isOkay() {
        return this.isOkay;
    }

    /**
     * Get the New Password.
     * 
     * @return New Password
     */
    public String getNewPassword() {
        return this.newPassword.getText();
    }

    /**
     * Get the Confirm Password.
     * 
     * @return Confirm Password
     */
    public String getConPassword() {
        return this.conPassword.getText();
    }
}
