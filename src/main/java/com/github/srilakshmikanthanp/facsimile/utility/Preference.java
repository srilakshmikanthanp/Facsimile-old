// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.utility;

import java.util.Arrays;
import java.util.prefs.*;
import com.github.srilakshmikanthanp.facsimile.consts.*;

/**
 * This class is used to store and retrieve the preferences the application.
 */
public class Preference {
    /**
     * PReference node for the application.
     */
    private static Preferences prefs = Preferences.userNodeForPackage(Preference.class);

    // Preference Mask one
    public static String MASK_ONE_KEY = "MASK_ONE";
    public static String MASK_ONE_DEFAULT = AppConsts.masks[0];

    /**
     * Get the mask one.
     * 
     * @return the mask one.
     */
    public static String getMaskOne() {
        return prefs.get(MASK_ONE_KEY, MASK_ONE_DEFAULT);
    }

    /**
     * Set the mask one.
     * 
     * @param maskOne the mask one.
     */
    public static void setMaskOne(String maskOne) {
        if(Arrays.asList(AppConsts.masks).contains(maskOne)) {
            prefs.put(MASK_ONE_KEY, maskOne);
        } else {
            throw new IllegalArgumentException("Invalid mask");
        }
    }

    // Preference Mask Two
    public static String MASK_TWO_KEY = "MASK_TWO";
    public static String MASK_TWO_DEFAULT = AppConsts.masks[1];

    /**
     * Get the mask one.
     * 
     * @return the mask one.
     */
    public static String getMaskTwo() {
        return prefs.get(MASK_TWO_KEY, MASK_TWO_DEFAULT);
    }

    /**
     * Set the mask one.
     * 
     * @param maskTwo the mask one.
     */
    public static void setMaskTwo(String maskTwo) {
        if(Arrays.asList(AppConsts.masks).contains(maskTwo)) {
            prefs.put(MASK_TWO_KEY, maskTwo);
        } else {
            throw new IllegalArgumentException("Invalid mask");
        }
    }

    // Preference Key value
    public static String KEY_VALUE_KEY = "KEY_VALUE";
    public static String KEY_VALUE_DEFAULT = "SEMICOLON";

    /**
     * Get the key value.
     * 
     * @return the key value.
     */
    public static String getKeyValue() {
        return prefs.get(KEY_VALUE_KEY, KEY_VALUE_DEFAULT);
    }

    /**
     * Set the key value.
     * 
     * @param keyValue the key value.
     */
    public static void setKeyValue(String keyValue) {
        prefs.put(KEY_VALUE_KEY, keyValue);
    }

    // preference for dark mode
    public static String DARK_MODE_KEY = "DARK_MODE";
    public static boolean DARK_MODE_DEFAULT = false;

    /**
     * Get the dark mode.
     * 
     * @return the dark mode.
     */
    public static boolean getDarkMode() {
        return prefs.getBoolean(DARK_MODE_KEY, DARK_MODE_DEFAULT);
    }

    /**
     * Set the dark mode.
     * 
     * @param darkMode the dark mode.
     */
    public static void setDarkMode(boolean darkMode) {
        prefs.putBoolean(DARK_MODE_KEY, darkMode);
    }

    /**
     * Add the preference change listener to the preference.
     * 
     * @param listener the preference change listener.
     */
    public static void addPreferenceChangeListener(PreferenceChangeListener listener) {
        prefs.addPreferenceChangeListener(listener);
    }
}
