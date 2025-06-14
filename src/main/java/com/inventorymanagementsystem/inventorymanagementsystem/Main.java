package com.inventorymanagementsystem.inventorymanagementsystem;

import com.inventorymanagementsystem.inventorymanagementsystem.utils.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
        } catch (Exception e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
        }

        launch();
    }
}