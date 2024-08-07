package com.example.jeecg_tools;

import cn.hutool.http.GlobalHeaders;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), -1, -1);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        stage.setTitle("Jeecg-exp-GUI");

        stage.setScene(scene);
        stage.show();
    }

//    public static void main(String[] args) {
//        launch();
//    }
}