package fxmlexample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by affo on 04/04/18.
 */
public class Main extends Application {
    private Scene scene1;
    private Scene scene2;

    public void start(Stage primaryStage) throws Exception {
        SingleButtonSceneManager manager1 = new SingleButtonSceneManager("plusone", n -> n + 1);
        SingleButtonSceneManager manager2 = new SingleButtonSceneManager("exp", n -> n * 2);

        scene1 = manager1.create();
        scene2 = manager2.create();

        // observe the manager to change scene
        manager1.addObserver((o, arg) -> {
            primaryStage.setScene(scene2);
        });

        manager2.addObserver((o, arg) -> {
            primaryStage.setScene(scene1);
        });

        primaryStage.setScene(scene1);
        primaryStage.setTitle("WeirdExample");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
