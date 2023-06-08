package com.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FXML.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("OS_Project");

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

//        schedular_Priority p = new schedular_Priority();
//
//        p.addProcess(0,3,1);
//        p.addProcess(1,2,2);
//        p.addProcess(1,3,0);
//
//        p.Preemptive_sortProcess();
//       // p.newsort_prem(3,1,2);
//        p.print();

      launch();
    }
}