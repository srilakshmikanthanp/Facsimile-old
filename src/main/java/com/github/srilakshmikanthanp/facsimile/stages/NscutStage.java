package com.github.srilakshmikanthanp.facsimile.stages;

import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.github.srilakshmikanthanp.facsimile.system.ShortCut;

/**
 * This class is used to Change ShortCut Key..
 */
public class NscutStage extends Stage 
{
    // button status
    public static final int OK_BUTTON = 1, CALCEL_BUTTON = 2;

    // Title
    private Label facsimile = new Label("Facsimile");

    // ShortCut Key lable
    private Label nscutLabel = new Label("Shortcut Key");

    // mask one box
    private ChoiceBox<String> maskOneBox = new ChoiceBox<>(
        FXCollections.observableArrayList(
            ShortCut.getKeys()
        )
    );

    // mask two box
    private ChoiceBox<String> maskTwoBox = new ChoiceBox<>(
        FXCollections.observableArrayList(
            ShortCut.getKeys()
        )
    );

    // TextField
    private TextField textField = new TextField();

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
        var hsctbox = new HBox(maskOneBox, maskTwoBox, textField);
        var hbtnbox = new HBox(okButton, cancelButton);
        var vbox = new VBox(nscutLabel, hsctbox, hbtnbox);

        // add event listeners
        okButton.setOnAction((evt) -> {
            this.buttonPressed = OK_BUTTON;
            this.hide();
        });

        cancelButton.setOnAction((evt) -> {
            this.buttonPressed = CALCEL_BUTTON;
            this.hide();
        });

        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    /**
     * Constructor for NscutStage.
     * 
     * @param parent Parent Stage.
     * @param errorFree Flag to indicate if the stage is in error free mode.
     */
    public NscutStage(Stage parent, boolean errorFree) 
    {
        // init modality, style and parent
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.TRANSPARENT);
        this.initOwner(parent);

        // Limit the Length of Text Field to one
        this.textField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) 
            {
                if(oldVal.intValue() > newVal.intValue() && textField.getText().length() > 1) 
                {
                    textField.setText(textField.getText().substring(0, 1));
                }
            }
        });

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
     * Return the Mask One Key.
     * 
     * @return Key
     */
    public String getMaskOne()
    {
        return this.maskOneBox.getValue();
    }

    /**
     * Return the Mask Two Key.
     * 
     * @return Key
     */
    public String getMaskTwo()
    {
        return this.maskTwoBox.getValue();
    }

    /**
     * Return the Text.
     * 
     * @return Text
     */
    public String getText()
    {
        return this.textField.getText();
    }

    /**
     * Return the status of the button.
     * 
     * @return status
     */
    public int getButtonPressed()
    {
        return this.buttonPressed;
    }
}
