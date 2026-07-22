package com.paqueteria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/Login.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("Sistema de Gestión de Paquetería");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}