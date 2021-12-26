// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile;

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import com.github.srilakshmikanthanp.facsimile.datum.*;
import com.github.srilakshmikanthanp.facsimile.stages.*;
import com.github.srilakshmikanthanp.facsimile.system.*;
import com.github.srilakshmikanthanp.facsimile.utility.*;

import org.jnativehook.NativeHookException;

/**
 * Main Panel for the Facsimile.
 */
class MainPane extends BorderPane
{

}

/**
 * Main Stage for the Facsimile.
 */
class MainStage extends Stage
{
    public MainStage(Stage pStage)
    {

    }
}

/**
 * Main Application class
 */
public class Facsimile extends Application 
{
    @Override
    public void start(Stage pStage) 
    {
        pStage.initStyle(StageStyle.UTILITY);
        pStage.setHeight(0.0);
        pStage.setWidth(0.0);
        pStage.setOpacity(0.0);
        pStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
