// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.utility;


import java.util.prefs.*;


/**
 * This class is used to store and retrieve the preferences the application.
 */
public class Preference 
{
    /**
     * PReference node for the application.
     */
    public static Preferences prefs = Preferences.userNodeForPackage(Preference.class);

    // Preference Mask one
    public static String MASK_ONE_KEY = "MASK_ONE";
    public static String MASK_ONE_DEFAULT = "CTRL";

    /**
     * Get the mask one.
     * 
     * @return the mask one.
     */
    public static String getMaskOne()
    {
        return prefs.get(MASK_ONE_KEY, MASK_ONE_DEFAULT);
    }

    /**
     * Set the mask one.
     * 
     * @param maskOne the mask one.
     */
    public static void setMaskOne(String maskOne)
    {
        prefs.put(MASK_ONE_KEY, maskOne);
    }
}
