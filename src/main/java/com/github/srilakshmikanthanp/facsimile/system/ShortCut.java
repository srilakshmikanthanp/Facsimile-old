// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.system;

import java.util.logging.*;
import org.jnativehook.keyboard.*;
import org.jnativehook.GlobalScreen;
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

        // register the preference event
        Preference.addPreferenceChangeListener((e) -> {
            this.loadFromPreference();
        });

        // reset
        LogManager.getLogManager().reset();

        // disable loging
        Logger logger = Logger.getLogger(
            GlobalScreen.class.getPackage().getName()
        );
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
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
     * Key Pressed Event
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent e)
    {;
        // define vars
        var modifiers = NativeKeyEvent.getModifiersText(
            e.getModifiers()
        );
        var key = NativeKeyEvent.getKeyText(
            e.getKeyCode()
        );
        
        // to upper case;;
        modifiers = modifiers.toUpperCase();
        key = key.toUpperCase();

        // check mask one contains
        if(!(modifiers.contains(mask_one)))
        {
            return;
        }

        // check mask two contains
        if(!(modifiers.contains(mask_two)))
        {
            return;
        }

        if(!key_str.equals(key))
        {
            return;
        }

        // run the runnable object
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
