package com.github.srilakshmikanthanp.facsimile.system;

import javafx.application.Platform;

import com.github.kwhat.jnativehook.keyboard.*;
import com.github.srilakshmikanthanp.facsimile.*;
import com.github.srilakshmikanthanp.facsimile.utility.Preference;

/**
 * System Keyboard Listener
 */
public class NativeKeyboard implements NativeKeyListener {
    // instance of NativeKeyboard
    private static NativeKeyboard instance;

    // Facsimile
    private Facsimile facsimile;

    /**
     * Escape Key Pressed
     */
    private void escapePressed() {
        Platform.runLater(() -> {
            facsimile.setVisible(false);
        });
    }

    /**
     * ShortCut Pressed
     */
    private void shortCutPressed() {
        Platform.runLater(() -> {
            facsimile.setVisible(true);
        });
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
    public void nativeKeyPressed(NativeKeyEvent e) {
        // define vars
        var mod = NativeKeyEvent.getModifiersText(
                e.getModifiers())
                .toUpperCase();
        var key = NativeKeyEvent.getKeyText(
                e.getKeyCode())
                .toUpperCase();

        // load from preference
        var mOne = Preference.getMaskOne();
        var mTwo = Preference.getMaskTwo();
        var kVal = Preference.getKeyValue();

        // check if shortcut is pressed
        if (mod.contains(mOne) && mod.contains(mTwo) && key.equals(kVal)) {
            this.shortCutPressed();
        } else if (key.equals("ESCAPE")) {
            this.escapePressed();
        }
    }

    /**
     * get the instance
     */
    public static NativeKeyboard getInstance() {
        if (instance == null) {
            instance = new NativeKeyboard();
        }
        return instance;
    }
}
