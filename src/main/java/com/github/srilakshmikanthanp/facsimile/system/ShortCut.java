// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.system;

import java.util.logging.*;
import org.jnativehook.keyboard.*;
import javafx.scene.input.KeyCode;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import com.github.srilakshmikanthanp.facsimile.utility.Preference;

/**
 * Class which handles the Global Key Event
 */
public class ShortCut extends NativeKeyAdapter
{
    // ctrl
    public static final String CTRL = "CTRL";

    // alt
    public static final String ALT = "ALT";

    // shift
    public static final String SHIFT = "SHIFT";

    // mask one
    private String mask_one;

    // mask two
    private String mask_two;

    // key code
    private String key_str;

    // Runnable object
    private Runnable runnable;

    /**
     * Saves the detials to PReference
     */
    private void saveToPreference()
    {
        Preference.setMaskOne(mask_one);
        Preference.setMaskTwo(mask_two);
        Preference.setKeyValue(key_str);
    }

    /**
     * Loads the detials from PReference
     */
    private void loadFromPreference()
    {
        this.mask_one = Preference.getMaskOne();
        this.mask_two = Preference.getMaskTwo();
        this.key_str = Preference.getKeyValue();
    }

    /**
     * Constructor
     * @param runnable Runnable object
     */
    public ShortCut(Runnable runnable)
    {
        // save the runnable object
        this.runnable = runnable;

        // load the detials from preference
        this.loadFromPreference();

        // reset
        LogManager.getLogManager().reset();

        // disable loging
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
    }

    /**
     * Sets the First mask
     * 
     * @param mask CTRL, ALT, SHIFT
     */
    public void setMaskOne(String mask)
    {
        if(mask.equals(CTRL)|| mask.equals(ALT) || mask.equals(SHIFT))
        {
            this.mask_one = mask;
            this.saveToPreference();
        }
        else
        {
            throw new IllegalArgumentException("Invalid mask");
        }
    }

    /**
     * Sets the Second mask
     * 
     * @param mask CTRL, ALT, SHIFT
     */
    public void setMaskTwo(String mask)
    {
        if(mask.equals(CTRL)|| mask.equals(ALT) || mask.equals(SHIFT))
        {
            this.mask_two = mask;
            this.saveToPreference();
        }
        else
        {
            throw new IllegalArgumentException("Invalid mask");
        }
    }

    /**
     * Sets the key code
     * 
     * @param key_char
     */
    public void setKeyChar(char key_char)
    {
        this.key_str = new String(new char[]{key_char});
        this.saveToPreference();
    }

    /**
     * sets the runnable object
     * 
     * @param runnable Runnable object
     */
    public void setRunnable(Runnable runnable)
    {
        this.runnable = runnable;
    }

    /**
     * Returns the runnable object
     */
    public Runnable getRunnable()
    {
        return runnable;
    }

    /**
     * Register the Global Key Event
     * 
     * @throws NativeHookException if fails
     */
    public void register() throws NativeHookException
    {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);
    }

    /**
     * Unregister the Global Key Event
     * 
     * @throws NativeHookException if fails
     */
    public void unregister() throws NativeHookException
    {
        GlobalScreen.removeNativeKeyListener(this);
        GlobalScreen.unregisterNativeHook();
    }

    /**
     * Key Pressed Event
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent e)
    {
        var modifiers = NativeKeyEvent.getModifiersText(e.getModifiers());
        var key = NativeKeyEvent.getKeyText(e.getKeyCode());
        
        modifiers = modifiers.toUpperCase();

        if(!(modifiers.contains(mask_one) && modifiers.contains(mask_two)))
        {
            return;
        }

        if(!key_str.equals(KeyCode.getKeyCode(key).getChar()))
        {
            return;
        }

        if(runnable == null)
        {
            return;
        }

        runnable.run();
    }

    /**
     * Shortcut Keys available
     * 
     * @return String[] keys
     */
    public static String[] getKeys()
    {
        return new String[]{CTRL, ALT, SHIFT};
    }
}
