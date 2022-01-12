package com.github.srilakshmikanthanp.facsimile.dialog;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import com.github.srilakshmikanthanp.facsimile.utility.*;

/**
 * Dialog used to change theme.
 */
public class ChangeTheme extends AbstractDialog {
    // List of themes
    private ComboBox<String> theme = new ComboBox<>();

    // label for the dialog
    private Label label = new Label("Change Theme");

    // is status okay for dialog
    private boolean isOkay = false;

    /**
     * Get the Content pane.
     */
    private Node getContent() {
        // init the label
        var image = new ImageView(
            getClass().getResource("/images/theme.png").toString()
        );
        image.setFitWidth(80);
        image.setFitHeight(80);
        label.setGraphic(image);
        label.setContentDisplay(ContentDisplay.TOP);

        // init the theme list
        theme.getItems().addAll(
            Preference.LIGHT,
            Preference.DARK
        );
        theme.setValue(Preference.getTheme());

        // init the layout
        var layout = new VBox(label, theme);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);

        // done
        return layout;
    }

    /**
     * Okay pressed Event.
     */
    @Override
    protected void okayPressed() {
        this.isOkay = true;
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
     * Constructor for the Dialog.
     * 
     * @param owner owner
     */
    public ChangeTheme(Window owner) {
        super(owner);
        this.setContent(getContent());
    }

    /**
     * Is the status of dialog in okay
     * 
     * @return boolean status
     */
    @Override
    public boolean isOkay() {
        return this.isOkay;
    }

    /**
     * Get the Selected of theme
     * 
     * @return theme
     */
    public String getTheme() {
        return this.theme.getSelectionModel().getSelectedItem();
    }
}
