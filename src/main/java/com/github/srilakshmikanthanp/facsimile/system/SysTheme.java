// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.system;

import javafx.scene.Scene;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import java.util.function.Consumer;
import com.jthemedetecor.OsThemeDetector;

/**
 * Class Helps to detect the OS theme.
 */
public class SysTheme {
    /**
     * Sets the Scene to Dark Theme
     * 
     * @param scene scene to set
     */
    private static void setDarkTheme(Scene scene) {
        // Resource
        var resource = SysTheme.class.getResource(
            "/styles/Dark.css"
        );
        // jmetro
        JMetro jMetro = new JMetro(
            Style.DARK
        );

        // with jmetro
        jMetro.setScene(
            scene
        );
        // Set the scene to dark theme
        scene.getStylesheets().add(
            resource.toExternalForm()
        );
    }

    /**
     * Sets the Scene to Light Theme
     * 
     * @param scene scene to set
     */
    private static void setLightTheme(Scene scene) {
        // Resource
        var resource = SysTheme.class.getResource(
            "/styles/LIGHT.css"
        );
        // jmetro
        JMetro jMetro = new JMetro(
            Style.LIGHT
        );

        // with jmetro
        jMetro.setScene(
            scene
        );
        // Set the scene to light theme
        scene.getStylesheets().add(
            resource.toExternalForm()
        );
    }

    /**
     * Sets the Scene to System Theme
     * 
     * @param scene scene to set
     */
    public static void setSystemTheme(Scene scene) {
        // System theme detector
        final var detector = OsThemeDetector.getDetector();

        // lambda to process
        Consumer<Boolean> process = (isDark) -> {
            if (isDark) { // Set the scene to dark theme
                setDarkTheme(scene);
            } else { // Set the scene to light theme
                setLightTheme(scene);
            }
        };

        // initialize the theme
        process.accept(
            detector.isDark()
        );
        // set the listener
        detector.registerListener(
            process
        );
    }
}
