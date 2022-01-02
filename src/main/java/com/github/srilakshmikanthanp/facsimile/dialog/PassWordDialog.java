package com.github.srilakshmikanthanp.facsimile.dialog;

import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import jfxtras.styles.jmetro.JMetroStyleClass;

import com.github.srilakshmikanthanp.facsimile.system.SysTheme;
import com.github.srilakshmikanthanp.facsimile.utility.Utilityfunc;

/**
 * Passwords Dialog
 */
public class PassWordDialog extends Stage
{
    // Get the password from user
    public static final int GINPUT_PASSWORD = 1;

    // create password from user
    public static final int CREATE_PASSWORD = 2;

    // change password from user
    public static final int CHANGE_PASSWORD = 3;

    // width of the stage
    private static final double width = 370;

    // height of the stage
    private static final double height = 350;

    // status of dialog
    private boolean isOk = false;

    // password old
    private String oldPassword = null;

    // password new
    private String actPassword = null;

    // type of dialog
    private int type = 0;

    /**
     * Create the Top Bar with cut button
     * 
     * @return Top Bar
     */
    private Node getTopBar() 
    {
        // define vars
        var clbl = new Label("☓");
        var cbtn = new Button();
        var span = new StackPane(clbl, cbtn);
        var hbox = new HBox(span);

        // init button
        cbtn.setOpacity(0);
        cbtn.setOnAction((evt) -> {
            this.isOk = false;
            this.oldPassword = null;
            this.actPassword = null;
            this.hide();
        });

        // init hbox
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setPadding(new Insets(10, 10, 10, 10));

        // done
        return hbox;
    }

    /**
     * Create the password Label to display
     * 
     * @return Label
     */
    private Label getPasswordLabel()
    {
        // label for password
        var label = new Label("Enter Password");
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-size: 20px;");

        // image view
        var image = new ImageView(
            getClass().getResource(
                "/images/user.png"
            ).toString()
        );

        // init label
        image.setFitHeight(100);
        image.setFitWidth(100);
        label.setGraphic(image);
        label.setContentDisplay(
            ContentDisplay.TOP
        );

        // done
        return label;
    }

    /**
     * Create the password TextField
     * 
     * @param next if has next password field
     * @return PasswordField field
     */
    private PasswordField getPasswordField(String prompt)
    {
        // password field
        var field = new PasswordField();
        field.setPromptText(prompt);
        field.setStyle("-fx-font-size: 15px;");
        field.setPrefHeight(30);
        field.setAlignment(Pos.CENTER_LEFT);

        // done
        return field;
    }

    /**
     * Sets the old password
     * 
     * @param pass old password
     */
    private void doneOldPassword(String pass)
    {
        this.oldPassword = pass;
    }

    /**
     * Sets the Actual password
     * 
     * @param pass new password
     */
    private void doneActPassword(String pass)
    {
        this.actPassword = pass;
        this.isOk = true;
        this.hide();
    }

    /**
     * Get the Cancel Button for the dialog
     * 
     * @return Button okay button
     */
    private Button getOkayButton()
    {
        // okay button
        var button = new Button("➜");
        button.setStyle("-fx-font-size: 15px;");
        button.setPrefHeight(50);
        button.setPrefWidth(100);
        button.setAlignment(Pos.CENTER);

        // done
        return button;
    }

    /**
     * Constructs the input pane
     * 
     * @return Pane
     */
    private Pane getGinputPane()
    {
        // defile vars
        var passLabel = this.getPasswordLabel();
        var passField = this.getPasswordField("Password");
        var okyButton = this.getOkayButton();

        // initileze the Field
        passField.setOnKeyPressed((evt) -> {
            if(evt.getCode() == KeyCode.ENTER) {
                okyButton.requestFocus();
            }
        });

        // initilize the button
        okyButton.setOnAction((evt) -> {
            this.doneActPassword(passField.getText());
        });

        // initilize the pane
        var pane = new VBox();

        // initilize the pane
        pane.setSpacing(15.0);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20.0));

        // add elements to the pane
        pane.getChildren().addAll(
            passLabel,
            passField,
            okyButton
        );

        // done
        return pane;
    }

    /**
     * Constructs the create pane
     * 
     * @return Pane
     */
    private Pane getCreatePane()
    {
        // defile vars
        var passLabel = this.getPasswordLabel();
        var npasField = this.getPasswordField("New Password");
        var cpasField = this.getPasswordField("Confirm Password");
        var okyButton = this.getOkayButton();

        // initileze the new password field
        npasField.setOnKeyPressed((evt) -> {
            if(evt.getCode() == KeyCode.ENTER) {
                cpasField.requestFocus();
            }
        });

        // initilize the confirm password field
        cpasField.setOnKeyPressed((evt) -> {
            if(evt.getCode() == KeyCode.ENTER) {
                okyButton.requestFocus();
            }
        });

        // initilize the button
        okyButton.setOnAction((evt) -> {
            var newpass = npasField.getText();
            var conpass = cpasField.getText();

            if(newpass.equals(conpass) && newpass.length() > 0) {
                this.doneActPassword(npasField.getText());
            } else {
                passLabel.setTextFill(Color.RED);
                npasField.requestFocus();
            }
        });

        // initilize the pane
        var pane = new VBox();

        // initilize the pane
        pane.setSpacing(15.0);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(20.0));

        // add elements to the pane
        pane.getChildren().addAll(
            passLabel,
            npasField,
            cpasField,
            okyButton
        );

        return pane;
    }

    /**
     * Constructs the change pane
     * 
     * @return Pane
     */
    private Pane getChangePane()
    {
        // defile vars
        var passLabel = this.getPasswordLabel();
        var opasField = this.getPasswordField("Old Password");
        var npasField = this.getPasswordField("New Password");
        var cpasField = this.getPasswordField("Confirm Password");
        var okyButton = this.getOkayButton();

        // initileze the Old PAssword Field
        opasField.setOnKeyPressed((evt) -> {
            if(evt.getCode() == KeyCode.ENTER) {
                npasField.requestFocus();
            }
        });

        // initilize the new password field
        npasField.setOnKeyPressed((evt) -> {
            if(evt.getCode() == KeyCode.ENTER) {
                cpasField.requestFocus();
            }
        });

        // initilize the confirm password field
        cpasField.setOnKeyPressed((evt) -> {
            if(evt.getCode() == KeyCode.ENTER) {
                okyButton.requestFocus();
            }
        });

        // initilize the button
        okyButton.setOnAction((evt) -> {
            var opass = opasField.getText();
            var npass = npasField.getText();
            var cpass = cpasField.getText();

            if(opass.length() == 0)
            {
                passLabel.setTextFill(Color.RED);
                opasField.requestFocus();
                return;
            }
            
            if(npass.equals(cpass) && npass.length() > 0) 
            {
                this.doneOldPassword(opasField.getText());
                this.doneActPassword(npasField.getText());
            }
            else 
            {
                passLabel.setTextFill(Color.RED);
                npasField.requestFocus();
            }
        });

        // initilize the pane
        var pane = new VBox();

        // initilize the pane
        pane.setSpacing(10.0);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10.0));

        // add elements to the pane
        pane.getChildren().addAll(
            passLabel,
            opasField,
            npasField,
            cpasField,
            okyButton
        );

        return pane;
    }

    /**
     * Returns the pane for thr type
     * 
     * @param Pane pane for type
     */
    private Pane getTypePane(int type)
    {
        switch(type)
        {
            case GINPUT_PASSWORD:
                return this.getGinputPane();
            case CREATE_PASSWORD:
                return this.getCreatePane();
            case CHANGE_PASSWORD:
                return this.getChangePane();
            default:
                return null;
        }
    }

    /**
     * Constructor for password dialog
     *
     * @param owner the owner of the dialog
     */
    public PassWordDialog(Window owner)
    {
        // init the stage
        this.initOwner(owner);
        this.initStyle(StageStyle.TRANSPARENT);
        this.initModality(Modality.APPLICATION_MODAL);

        // set on showing
        this.setOnShowing((evt) -> {
            if(this.type == 0) {
                throw new RuntimeException("Type is not set");
            }
        });

        // center the stage
        this.setOnShown((evt) -> {
            // set the dimension
            this.setMinWidth(width);
            this.setMinHeight(height);

            // center the stage or screen
            if(owner == null)
            {
                Utilityfunc.centerToScreen(this);
            }
            else if(owner.isShowing()) 
            {
                Utilityfunc.centerTo(
                    owner, this
                );
            } 
            else 
            {
                Utilityfunc.centerTo(
                    null, this
                );
            }

            // to front
            this.toFront();
        });

        // icon
        var iconStream = this.getClass().getResourceAsStream(
            "/images/logo.png"
        );
        this.getIcons().addAll(
            new Image(iconStream)
        );
    }

    /**
     * Sets the type of password
     * 
     * @param type type of password
     */
    public void setType(int type)
    {
        // set the type
        switch(type)
        {
            // check for validity
            case GINPUT_PASSWORD:
            case CREATE_PASSWORD:
            case CHANGE_PASSWORD:
                this.type = type;
                break;
            // if not throw an error
            default:
                throw new RuntimeException("Invalid type");
        }

        // define the pane
        var pane = new BorderPane(
            this.getTypePane(type)
        );

        // init style
        pane.getStyleClass().add(
            "stage-main-pane"
        );

        // init the pane
        pane.setTop(this.getTopBar());
        
        // stackpane
        var stackPane = new StackPane(
            new VBox(pane)
        );
        var scene = new Scene(
            stackPane
        );

        // init pane
        scene.setFill(Color.TRANSPARENT);
        stackPane.getStyleClass().add(
            "stage-bg-pane"
        );

        // set scene
        this.setScene(scene);

        // set theme
        SysTheme.setSystemTheme(
            this.getScene()
        );

        // jmetro
        pane.getStyleClass().add(
            JMetroStyleClass.BACKGROUND
        );
    }

    /**
     * Is the dialog ok
     * 
     * @return status
     */
    public boolean isOkay()
    {
        return this.isOk;
    }

    /**
     * Gets the Old Password of the user
     * 
     * @return String old password
     */
    public String getOldPassword()
    {
        return this.oldPassword;
    }

    /**
     * Gets the New Password of the user
     * 
     * @return String new password
     */
    public String getActPassword()
    {
        return this.actPassword;
    }
}
