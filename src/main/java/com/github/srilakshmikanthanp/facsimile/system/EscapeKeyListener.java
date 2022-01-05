package com.github.srilakshmikanthanp.facsimile.system;

import com.github.kwhat.jnativehook.keyboard.*;

public class EscapeKeyListener implements NativeKeyListener {
    // Runnabel action
    private Runnable runnable = null;

    /**
     * Constructor
     * 
     * @param runnabel runnabel
     */
    public EscapeKeyListener(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * sets the runnable object
     * 
     * @param runnable Runnable object
     */
    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Returns the runnable object
     */
    public Runnable getRunnable() {
        return runnable;
    }
    
    /**
     * Key Pressed Event
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        var keycode = e.getKeyCode();
        var keyPressed = NativeKeyEvent.getKeyText(keycode);
        var escapeKey = "ESCAPE";

        if(keyPressed.equalsIgnoreCase(escapeKey)) {
            if(runnable != null) {
                runnable.run();
            }
        }
    }
}
