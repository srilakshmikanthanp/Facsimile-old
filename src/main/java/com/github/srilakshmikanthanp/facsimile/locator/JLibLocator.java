// Copyright (c) 2022 srilakshmikanthanp
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.locator;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import com.github.kwhat.jnativehook.NativeLibraryLocator;

/**
 * This class is used to locate the native libraries.
 */
public class JLibLocator implements NativeLibraryLocator {
    /**
     * This method is used to regsiter the Locator.
     */
    public static void setAaDefaultLocator() {
        System.setProperty("jnativehook.lib.locator", JLibLocator.class.getCanonicalName());
    }

    /**
     * Locates the native libraries.
     */
    @Override
    public Iterator<File> getLibraries() {
        var libs = new ArrayList<File>(1);
        var os = SystemInfo.getFamily().toString().toLowerCase();
        var arch = SystemInfo.getArchitecture().toString().toLowerCase();
        var jhome = System.getProperty("java.home");
        var libName = System.mapLibraryName("JNativeHook");
        var lib = jhome + File.separator + os + File.separator + arch + File.separator + libName;
        var libFile = new File(lib);

        libs.add(libFile);

        return libs.iterator();
    }
}
