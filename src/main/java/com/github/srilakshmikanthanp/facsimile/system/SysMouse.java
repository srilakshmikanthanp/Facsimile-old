package com.github.srilakshmikanthanp.facsimile.system;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputAdapter;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

/**
 * Class which handles the Global Mouse Event
 */
public class SysMouse extends NativeMouseInputAdapter implements NativeMouseWheelListener
{
    /**
     * BAsic Mouse Listener
     */
    public interface BasicMouseListener
    {
        void actionPerformed(NativeMouseEvent e);
    }

    // Runabble action
    private BasicMouseListener runnable;

    /**
     * Constructor
     * @param runnable Runnable object
     */
    public SysMouse(BasicMouseListener runnable)
    {
        this.runnable = runnable;
    }

    /**
     * Set's the runnabel object
     * 
     * @param runnable Runnable object
     */
    public void setActionListener(BasicMouseListener runnable)
    {
        this.runnable = runnable;
    }

    /**
     * Get's the runnable object
     * 
     * @return Runnable object
     */
    public BasicMouseListener getActionListener()
    {
        return this.runnable;
    }

    /**
     * Method that called on mouse Press
     * 
     * @param e Mouse event
     */
    @Override
    public void nativeMousePressed(NativeMouseEvent evt) 
    {
        if(runnable != null)
        {
            runnable.actionPerformed(evt);
        }
    }

    /**
     * Method that called on mouse Wheel Moved
     * 
     * @param e Mouse event
     */
    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent evt) 
    {
        if(runnable != null)
        {
            runnable.actionPerformed(evt);
        }
    }
}
