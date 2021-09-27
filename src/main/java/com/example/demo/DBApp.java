package com.example.demo;

import com.example.demo.persistence.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DBApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DBController.class.getResource("GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400.0, 580.0);
        stage.setTitle("Database");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(closeEvent -> {
            HibernateUtil.closeSession();
            HibernateUtil.closeSessionFactory();
            System.out.println("Application closing...");
        });
    }


    public static void main(String[] args) {
        launch();
    }
}