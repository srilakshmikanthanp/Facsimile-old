package com.github.srilakshmikanthanp.facsimile.dialog;

import com.github.srilakshmikanthanp.facsimile.utility.Utilityfuns;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * Base dialog class.
 */
public abstract class AbstractDialog extends Stage {
    // Min height of the dialog.
    private static final double minStageHeight = 370.0;

    // Min width of the dialog.
    private static final double minStageWidth = 350.0;

    // Border pane for the stage
    private BorderPane borderPane = new BorderPane();

    /**
     * Create the Top Bar with cut button
     * 
     * @return Top Bar
     */
    private void setTopPane() {
        // define vars
        var clbl = new Label("☓");
        var cbtn = new Button();
        var span = new StackPane(clbl, cbtn);
        var hbox = new HBox(span);

        // init button
        cbtn.setOpacity(0);
        cbtn.setOnAction((evt) -> {
            this.cutPressed();
        });

        // init hbox
        hbox.setAlignment(
            Pos.CENTER_RIGHT
        );

        // set
        borderPane.setTop(hbox);
    }

    /**
     * Sets the Content of the dialog.
     * 
     * @param content
     */
    private void setMidPane(Node content) {
        borderPane.setCenter(content);
    }

    /**
     * Set the Bottom pane
     */
    private void setBotPane() {
        // define vars
        var obtn = new Button("➜");
        var hbox = new HBox(obtn);

        // init button
        obtn.setOnAction((evt) -> {
            this.okayPressed();
        });

        // init hbox
        hbox.setAlignment(
            Pos.CENTER
        );

        // set
        borderPane.setBottom(hbox);
    }

    /**
     * Gets the stacke pane
     * 
     * @return Pane
     */
    private Pane getStackPane()
    {
        // define vars
        var pane = new StackPane(borderPane);

        // init border pane
        borderPane.setId("main-pane");

        // init pane
        pane.setId("container");
        pane.setPadding(new Insets(10));

        // done
        return pane;
    }

    /**
     * Mthod that call on OK button click.
     */
    protected abstract void okayPressed();

    /**
     * Mthod that call on Cancel button click.
     */
    protected abstract void cutPressed();

    /**
     * Get ths atatus of the Diaholg
     */
    protected abstract boolean isOkay();

    /**
     * Constructor.
     */
    public AbstractDialog(Window owner) {
        // intilize the stage
        this.initOwner(owner);
        this.initStyle(StageStyle.TRANSPARENT);
        this.initModality(Modality.APPLICATION_MODAL);

        // set the panes
        this.setTopPane();
        this.setBotPane();

        // set the scene
        this.setScene(new Scene(getStackPane()));

        // on shown
        this.setOnShown((evt) -> {
            this.toFront();
            this.setMinWidth(minStageWidth);
            this.setMinHeight(minStageHeight);
            Utilityfuns.centerTo(owner, this);
        });

        // set padding
        borderPane.setPadding(new Insets(5));
    }

    /**
     * Set the content of the dialog.
     * 
     * @param content
     */
    public void setContent(Node content) {
        this.setMidPane(content);
    }
}
