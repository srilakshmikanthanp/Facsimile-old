package com.github.srilakshmikanthanp.facsimile.utility;

import java.io.*;
import java.net.ServerSocket;

import javafx.stage.*;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

import com.github.srilakshmikanthanp.facsimile.consts.*;

/**
 * Some Utility Functions
 */
public class Utilityfuns {
    /**
     * Shows the alert.
     * 
     * @param ex Exception to show
     */
    public static boolean showError(Exception ex) {
        // create alert box
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

        // lable stack trace
        Label label = new Label("The exception stacktrace was:");

        // set the stack trace
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

        // show on center
        alert.setOnShown((evt) -> {
            var bounds = Screen.getPrimary().getVisualBounds();
            var wX = (bounds.getWidth() - alert.getWidth()) / 2;
            var wY = (bounds.getHeight() - alert.getHeight()) / 2;
            alert.setX(bounds.getMinX() + wX);
            alert.setY(bounds.getMinY() + wY);
        });

        // show alert
        var res = alert.showAndWait();

        // if not valid
        if (!res.isPresent()) {
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
    public static void centerToScreen(Window window) {
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
    public static void centerTo(Window parent, Window window) {
        // if parent is null or not showing
        if (parent == null || !parent.isShowing()) {
            centerToScreen(window);
            return;
        }

        // set the stage to center of the parent
        var advX = (parent.getWidth() - window.getWidth()) / 2;
        var advY = (parent.getHeight() - window.getHeight()) / 2;
        window.setX(parent.getX() + advX);
        window.setY(parent.getY() + advY);
    }

    /**
     * Returns the status of application running
     * 
     * @return status
     */
    public static boolean isApplicationRunning() {
        try {
            // check if port is available
            ServerSocket socket = new ServerSocket(AppConsts.SOCKET_PORT);

            // close the socket on stop
            Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    try { socket.close();} catch (IOException e) {}
                }, "SocketCloser")
            );

            // if port is available, not running
            return false;
        } catch (IOException e) {
            // if port is not available, running
            return true;
        }
    }

    /**
     * Copy the String to ClipBoard
     * 
     * @param data The data to be copied
     */
    public static void copyToClipboard(String data) {
        // create clipboard
        var clipboard = Clipboard.getSystemClipboard();

        // create content
        var content = new ClipboardContent();
        content.putString(data);

        // set content
        clipboard.setContent(content);
    }

    /**
     * Set the user theme
     */
    public static void setUserTheme(Scene scene) {
        // initial theme
        var style = Style.LIGHT;
        var css = "/styles/Light.css";

        // check theme
        if(Preference.getTheme().equals(Preference.DARK)) {
            style = Style.DARK;
            css = "/styles/Dark.css";
        }

        // set theme
        JMetro jMetro = new JMetro(style);

        // set theme
        jMetro.setScene(scene);

        // set css
        scene.getStylesheets().add(
            Utilityfuns.class.getResource(css).toExternalForm()
        );

        // set jmetro
        for(var par : scene.getRoot().getChildrenUnmodifiable()) {
            if(par instanceof Parent) {
                par.getStyleClass().add(
                    JMetroStyleClass.BACKGROUND
                );
            }
        }

        // set jmetro
        scene.getRoot().getStyleClass().add(
            JMetroStyleClass.BACKGROUND
        );
    }
}
