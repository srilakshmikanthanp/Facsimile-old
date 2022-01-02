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
 * A Stage that displays a option to add key-value pair.
 */
public class KeyValueDialog extends Stage
{
    // width of the stage
    private static final double width = 370;

    // height of the stage
    private static final double height = 350;

    // status of dialog
    private boolean isOk = false;

    // key
    private String key;

    // value
    private String value;

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
            this.key = null;
            this.value = null;
            this.hide();
        });

        // init hbox
        hbox.setAlignment(
            Pos.CENTER_RIGHT
        );

        // return
        return hbox;
    }

    /**
     * Create the content of the dialog.
     * 
     * @return content
     */
    private Node getContent()
    {
        // define vars
        var label = new Label("Key Value");
        var keyField = new TextField();
        var valueField = new TextField();
        var btn = new Button("➜");

        // init label
        var image = new ImageView(
            getClass().getResource(
                "/images/keyvalue.png"
            ).toString()
        );
        image.setFitWidth(100);
        image.setFitHeight(100);
        label.setGraphic(image);
        label.setContentDisplay(
            ContentDisplay.TOP
        );

        // init KeyField
        keyField.setPromptText("Key");
        keyField.setOnKeyPressed((evt) -> {
            if(evt.getCode() == KeyCode.ENTER) {
                valueField.requestFocus();
            }
        });

        // init ValueField
        valueField.setPromptText("Value");
        valueField.setOnKeyPressed((evt) -> {
            if(evt.getCode() == KeyCode.ENTER) {
                btn.requestFocus();
            }
        });

        // init button
        btn.setOnAction((evt) -> {
            // check if key is empty
            if(!keyField.getText().isEmpty()) {
                this.key = keyField.getText();
                this.value = valueField.getText();
                this.isOk = true;
                this.hide();
            } else {
                keyField.requestFocus();
            }
        });

        // init vbox
        var vbox = new VBox(
            label, keyField, valueField, btn
        );
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        // done
        return vbox;
    }

    /**
     * Constructor.
     */
    public KeyValueDialog(Window owner)
    {
        // init the stage
        this.initOwner(owner);
        this.initStyle(StageStyle.TRANSPARENT);
        this.initModality(Modality.APPLICATION_MODAL);

        // define the pane
        var pane = new BorderPane(
            this.getContent()
        );

        // init style
        pane.getStyleClass().add(
            "stage-main-pane"
        );

        // init the pane
        pane.setPadding(new Insets(10));
        pane.setTop(this.getTopBar());

        // center the stage
        this.setOnShown((evt) -> {
            // set min max dimensions
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

        // stackpane
        var stackPane = new StackPane(
            pane
        );
        var scene = new Scene(
            stackPane
        );

        // init pane
        stackPane.setPadding(new Insets(20));
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

        // icon
        var iconStream = this.getClass().getResourceAsStream(
            "/images/logo.png"
        );
        this.getIcons().addAll(
            new Image(iconStream)
        );
    }

    /**
     * is the dialog ok?
     * 
     * @return true if ok
     */
    public boolean isOk()
    {
        return this.isOk;
    }

    /**
     * Get the key.
     * 
     * @return key
     */
    public String getKey()
    {
        return this.key;
    }

    /**
     * Get the value.
     * 
     * @return value
     */
    public String getValue()
    {
        return this.value;
    }
}
