// Copyright (c) 2021 Sri Lakshmi Kanthan P
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

module facsimile {
    // from java
    requires java.prefs;
    requires java.logging;
    requires java.desktop;

    // from gson
    requires com.google.gson;

    // from jnative hook
    requires com.github.kwhat.jnativehook;

    // from javafx
    requires javafx.controls;

    // from jmetro
    requires org.jfxtras.styles.jmetro;

    // give access to the facsimile package
    opens com.github.srilakshmikanthanp.facsimile to javafx.graphics;
}
