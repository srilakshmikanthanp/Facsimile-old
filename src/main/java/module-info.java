// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

module facsimile 
{
    // from java
    requires java.prefs;

    // from javafx
    requires javafx.controls;

    // give access to the facsimile package
    opens com.github.srilakshmikanthanp.facsimile to javafx.graphics;
}
