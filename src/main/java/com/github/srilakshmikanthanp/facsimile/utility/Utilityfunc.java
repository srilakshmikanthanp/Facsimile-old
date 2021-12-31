package com.github.srilakshmikanthanp.facsimile.utility;

import java.io.*;

import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

/**
 * Some Utility Functions
 */
public class Utilityfunc 
{
    /**
     * Shows the alert.
     * 
     * @param ex Exception to show
     */
    public static boolean showError(Exception ex)
    {
        // create alert
        var alert = new Alert(AlertType.ERROR);

        // init the alert
        alert.setTitle("Internal Error");
        alert.setHeaderText("Exception occured");
        alert.setContentText(ex.getMessage());

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        // set the stack trace
        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(
            expContent
        );

        // show alert
        var res = alert.showAndWait();

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
