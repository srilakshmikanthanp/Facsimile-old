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
public class AlterPassword extends AbstractDialog {
    // old password
    private PasswordField oldPassword = new PasswordField();

    // new password
    private PasswordField newPassword = new PasswordField();

    // confirm password
    private PasswordField conPassword = new PasswordField();

    // Label for the Dialog
    private Label label = new Label("Change Password");

    // dialog status
    private boolean isOkay = false;

    // min password length
    private int minLength = 8;

    /**
     * Get the Content pane.
     */
    @Override
    protected Node getContent() {
        // initlize the label
        var image = new ImageView(
            getClass().getResource("images/user.png").toString()
        );
        label.setGraphic(image);
        label.setGraphicTextGap(10);
        label.setContentDisplay(ContentDisplay.TOP);

        // initileze the Old PAssword Field
        oldPassword.setOnKeyPressed((evt) -> {
            if (evt.getCode() == KeyCode.ENTER) {
                newPassword.requestFocus();
            }
        });

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
            label, oldPassword, newPassword, conPassword
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
    public AlterPassword(Window owner) {
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
    public boolean getStatus() {
        return this.isOkay;
    }

    /**
     * Get the Old Password.
     * 
     * @return Old Password
     */
    public String getOldPassword() {
        return this.oldPassword.getText();
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
