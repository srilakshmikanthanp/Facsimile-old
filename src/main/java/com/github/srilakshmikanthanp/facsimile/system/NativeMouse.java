package com.github.srilakshmikanthanp.facsimile.system;

import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import com.github.kwhat.jnativehook.mouse.*;
import com.github.srilakshmikanthanp.facsimile.*;

/**
 * System Keyboard Listener
 */
public class NativeMouse implements NativeMouseInputListener {
    // instance of NativeKeyboard
    private static NativeMouse instance;

    // Facsimile
    private Facsimile facsimile;

    /**
     * Mouse Clicked
     */
    private void mouseClicked(int x, int y) {
        // Rectangle
        var rect = new Rectangle(
                facsimile.getX(),
                facsimile.getY(),
                facsimile.getWidth(),
                facsimile.getHeight());

        var sclX = Screen.getPrimary().getOutputScaleX();
        var sclY = Screen.getPrimary().getOutputScaleY();
        var posX = x / sclX;
        var posY = y / sclY;

        // if not in stage
        if (!rect.contains(posX, posY)) {
            facsimile.setVisible(false);
        }
    }

    /**
     * Sets the Facsimile instance
     * 
     * @param facsimile instance
     */
    public void setFacsimile(Facsimile facsimile) {
        this.facsimile = facsimile;
    }

    /**
     * Key press Event
     */
    @Override
    public void nativeMouseClicked(NativeMouseEvent evt) {
        Platform.runLater(() -> {
            this.mouseClicked(evt.getX(), evt.getY());
        });
    }

    /**
     * get the instance
     */
    public static NativeMouse getInstance() {
        if (instance == null) {
            instance = new NativeMouse();
        }
        return instance;
    }
}
