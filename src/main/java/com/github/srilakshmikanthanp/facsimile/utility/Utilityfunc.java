package com.github.srilakshmikanthanp.facsimile.utility;

import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/**
 * Some Utility Functions
 */
public class Utilityfunc 
{
    /**
     * Shows the alert.
     */
    public static boolean showError(String content)
    {
        // create alert
        var alert = new Alert(AlertType.ERROR);

        // init the alert
        alert.setTitle("Internal Error");
        alert.setHeaderText("Exception occured");
        alert.setContentText(content);

        // show alert
        var res =alert.showAndWait();

        // if not valid
        if(!res.isPresent())
        {
            return false;
        }

        // return result
        return res.get() == ButtonType.OK;
    }
    
    /**
     * Sets the stage to center of the screen.
     * 
     * @param stage The stage to be set.
     */
    public static void centerToScreen(Window window)
    {
        var bounds = Screen.getPrimary().getVisualBounds();
        var wX = (bounds.getWidth() - window.getWidth()) / 2;
        var wY = (bounds.getHeight() - window.getHeight()) / 2;
        window.setX(bounds.getMinX() + wX);
        window.setY(bounds.getMinY() + wY);
    }

    /**
     * Sets stage center to the parent if parent is null 
     * then to the screne.
     * 
     * @param parent The parent stage.
     * @param window The stage to be set.
     */
    public static void centerTo(Window parent, Window window)
    {
        if(parent != null)
        {
            var advX = (parent.getWidth() - window.getWidth()) / 2;
            var advY = (parent.getHeight() - window.getHeight()) / 2;
            window.setX(parent.getX() + advX);
            window.setY(parent.getY() + advY);
        }
        else 
        {
            centerToScreen(window);
        }
    }
}
