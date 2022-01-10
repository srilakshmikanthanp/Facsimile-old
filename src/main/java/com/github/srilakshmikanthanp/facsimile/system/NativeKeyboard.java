package com.github.srilakshmikanthanp.facsimile.system;

import com.github.kwhat.jnativehook.*;
import com.github.kwhat.jnativehook.keyboard.*;
import com.github.srilakshmikanthanp.facsimile.Facsimile;

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

    }

    /**
     * ShortCut Pressed
     */
    private void shortCutPressed() {

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
