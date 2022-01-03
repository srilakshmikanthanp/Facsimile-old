package com.github.srilakshmikanthanp.facsimile.system;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseWheelListener;

/**
 * Class which handles the Global Mouse Event
 */
public class SysMouse implements NativeMouseListener, NativeMouseWheelListener {
    /**
     * BAsic Mouse Listener
     */
    public interface MousePositionListener {
        void actionPerformed(int x, int y);
    }

    // Runabble action
    private MousePositionListener action;

    /**
     * Constructor
     * 
     * @param runnable Runnable object
     */
    public SysMouse(MousePositionListener runnable) {
        this.action = runnable;
    }

    /**
     * Set's the runnabel object
     * 
     * @param runnable Runnable object
     */
    public void setActionListener(MousePositionListener runnable) {
        this.action = runnable;
    }

    /**
     * Get's the runnable object
     * 
     * @return Runnable object
     */
    public MousePositionListener getActionListener() {
        return this.action;
    }

    /**
     * Method that called on mouse Press
     * 
     * @param e Mouse event
     */
    @Override
    public void nativeMousePressed(NativeMouseEvent evt) {
        if (action != null) {
            action.actionPerformed(evt.getX(), evt.getY());
        }
    }

    /**
     * Method that called on mouse Wheel Moved
     * 
     * @param e Mouse event
     */
    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent evt) {
        if (action != null) {
            action.actionPerformed(evt.getX(), evt.getY());
        }
    }
}
