package com.github.srilakshmikanthanp.facsimile.dialog;

import javafx.scene.Node;
import javafx.stage.Window;

/**
 * PAssword Input Dialog.
 */
public class InputShortCut extends AbstractDialog {
    /**
     * Get the Content pane.
     */
    @Override
    protected Node getContent() {
        return null;
    }

    /**
     * Okay pressed Event.
     */
    @Override
    protected void okayPressed() {
        
    }

    /**
     * Cancel pressed Event.
     */
    @Override
    protected void cutPressed() {

    }

    /**
     * Constructor for Password Input Dialog.
     * 
     * @param owner owner
     */
    public InputShortCut(Window owner) {
        super(owner);
    }    
}
