// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

@SuppressWarnings("module")
module facsimile 
{
    // from java
    requires java.prefs;
    requires java.logging;
    requires java.desktop;

    // from gson
    requires com.google.gson;

    // from jnative hook
    requires jnativehook;

    // from javafx
    requires javafx.controls;

    // theme decetcor
    requires com.jthemedetector;

    // give access to the facsimile package
    opens com.github.srilakshmikanthanp.facsimile to javafx.graphics;
}
