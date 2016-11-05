/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Author: Matthew Martinez
 * Instrcutor: Dr. Quweider
 * Description: This program is a GUI for a user to edit photos
 */
package dip_javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author MQ0162246
 */
public class DIPJavaFXMainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        
        Pane root = new GUIBorderPane();
        Scene scene = new Scene(root);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DIP with Java FX");
        primaryStage.getIcons().addAll(new Image("utb.jpg"));
        primaryStage.show();
        
    }
    
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        launch(args);
    }
}
