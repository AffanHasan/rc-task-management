/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc_task_management;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.LinkedList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author root
 */
public class Rc_task_management extends Application {
    
    private VBox root;

    private void init(Stage primaryStage){
        root = new VBox();
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Royal Cyber Task Management");
        primaryStage.setScene(new Scene(root, 1500, 700));
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.init(primaryStage);
        primaryStage.show();
        DaoFactory.getInstance().getDao();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}