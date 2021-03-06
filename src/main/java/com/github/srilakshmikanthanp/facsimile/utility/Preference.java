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
    public static String MASK_ONE_DEFAULT = AppConsts.masks[3];

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
    public static String MASK_TWO_DEFAULT = AppConsts.masks[3];

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
    public static String KEY_VALUE_DEFAULT = AppConsts.keys[4];

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
    public static String THEME_KEY = "THEME";
    public static String LIGHT = "LIGHT";
    public static String DARK = "DARK";

    /**
     * Get the dark mode.
     * 
     * @return the dark mode.
     */
    public static String getTheme() {
        return prefs.get(THEME_KEY, LIGHT);
    }

    /**
     * Set the dark mode.
     * 
     * @param darkMode the dark mode.
     */
    public static void setTheme(String darkMode) {
        prefs.put(THEME_KEY, darkMode);
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
