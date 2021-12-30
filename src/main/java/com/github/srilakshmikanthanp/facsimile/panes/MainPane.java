package com.github.srilakshmikanthanp.facsimile.panes;

import javafx.scene.layout.*;
import javafx.geometry.Insets;
import com.github.srilakshmikanthanp.facsimile.datum.*;

/**
 * Main pane for the Facsimile application
 */
public class MainPane extends BorderPane
{
    /**
     * Constructor for the main pane
     * 
     * @param mapping mapping
     */
    public MainPane(Mapping mapping)
    {
        // Set the Three Panes
        this.setTop(new TopPane(mapping));
        this.setCenter(new MidPane(mapping));
        this.setBottom(new BotPane(mapping));


        // init the pane
        this.setPadding(new Insets(10));
    }
}
