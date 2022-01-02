package com.github.srilakshmikanthanp.facsimile.dialog;

import java.util.Arrays;

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

import com.github.srilakshmikanthanp.facsimile.system.*;
import com.github.srilakshmikanthanp.facsimile.utility.*;

/**
 * Class to set a shortcut key for dilog.
 */
public class ShortCutDialog extends Stage
{
    // width of the stage
    private static final double width = 370;

    // height of the stage
    private static final double height = 350;

    // status of dialog
    private boolean isOk = false;

    // mask one
    private String maksOne;

    // mask two
    private String maksTwo;

    // key
    private String key;

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
            this.maksOne = null;
            this.maksTwo = null;
            this.key = null;
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
     * Constructs and Gives the Connent Pane
     * 
     * @return Pane
     */
    private Node getCenterPane() 
    {
        // define vars
        var shLabel = new Label("Shortcut");
        var maskOne = new ChoiceBox<String>();
        var maskTwo = new ChoiceBox<String>();
        var keyValue = new ChoiceBox<String>();
        var okybutn = new Button("➜");

        // init the label
        var image = new ImageView(
            getClass().getResource(
                "/images/shortcut.png"
            ).toString()
        );
        image.setFitHeight(100);
        image.setFitWidth(100);
        shLabel.setGraphic(image);
        shLabel.setContentDisplay(
            ContentDisplay.TOP
        );

        // init the mask one
        maskOne.getItems().addAll(
            ShortCut.getKeys()
        );
        maskOne.setValue(
            Preference.getMaskOne()
        );

        // init the mask two
        maskTwo.getItems().addAll(
            ShortCut.getKeys()
        );
        maskTwo.setValue(
            Preference.getMaskTwo()
        );

        // init the keyValue
        var list = Arrays.asList(
            KeyCode.values()
        ).stream().map(
            (key) -> key.getName().toUpperCase()
        ).collect(
            java.util.stream.Collectors.toList()
        );
        list.sort(
            (a, b) -> a.compareTo(b)
        );
        list.removeAll(
            Arrays.asList(ShortCut.getKeys())
        );
        keyValue.getItems().addAll(
            list
        );
        keyValue.setValue(
            Preference.getKeyValue()
        );

        // init the ok button
        okybutn.setOnAction((evt) -> {
            this.maksOne = maskOne.getValue().toUpperCase();
            this.maksTwo = maskTwo.getValue().toUpperCase();
            this.key = keyValue.getValue().toUpperCase();
            this.isOk = true;
            this.hide();
        });

        // define the pane
        var shrtpane = new VBox(maskOne, maskTwo, keyValue);
        var mainpane = new VBox(shLabel, shrtpane, okybutn);

        // init the pane
        shrtpane.setSpacing(10);
        mainpane.setSpacing(25);
        shrtpane.setAlignment(Pos.CENTER);
        mainpane.setAlignment(Pos.CENTER);

        // done
        return mainpane;
    }

    /**
     * Constructir for Shrotcut Dialog
     * 
     * @param owner owner window
     */
    public ShortCutDialog(Window owner)
    {
        // init the stage
        this.initOwner(owner);
        this.initStyle(StageStyle.TRANSPARENT);
        this.initModality(Modality.APPLICATION_MODAL);

        // define the pane
        var pane = new BorderPane(
            this.getCenterPane()
        );

        // init style
        pane.getStyleClass().add(
            "stage-main-pane"
        );

        // init the pane
        pane.setTop(this.getTopBar());

        // center the stage
        this.setOnShown((evt) -> {
            // set min max dimensions
            this.setMinWidth(width);
            this.setMinHeight(height);

            // center the stage or screen
            if(owner == null)
            {
                Utilityfunc.centerToScreen(
                    this
                );
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
     * Is State Okay
     * 
     * @return state
     */
    public boolean isOk()
    {
        return isOk;
    }

    /**
     * Get the mask One
     * 
     * @return Mask one value
     */
    public String getMaskOne()
    {
        return maksOne;
    }

    /**
     * Get the mask Two
     * 
     * @return Mask two value
     */
    public String getMaskTwo()
    {
        return maksTwo;
    }    

    /**
     * Get the Key
     * 
     * @return Key value
     */
    public String getKey()
    {
        return key;
    }
}
