package com.github.srilakshmikanthanp.facsimile.utility;

import javafx.stage.*;
import javafx.geometry.Rectangle2D;

/**
 * Stage utilities
 */
public class StageUtility 
{
    /**
     * Centers the stage on the screen
     * 
     * @param stage stage to center
     */
    public static void centerStageOnScreen(Stage stage)
    {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        var xPosition = (bounds.getWidth() - stage.getWidth()) / 2;
        var yPosition = (bounds.getHeight() - stage.getHeight()) / 2;
        stage.setX(xPosition);
        stage.setY(yPosition);
    }

    /**
     * Return the empty stage
     * 
     * @return stage
     */
    public static Stage getEmptyStage()
    {
        // create stage
        var stage = new Stage();

        // init Empty stage
        stage.initStyle(StageStyle.UTILITY);
        stage.setHeight(0.0);
        stage.setWidth(0.0);
        stage.setOpacity(0.0);
        stage.show();

        // done
        return stage;
    }
}
