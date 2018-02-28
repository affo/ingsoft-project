package it.polimi.beat.fx;

import it.polimi.beat.BeatModel;
import it.polimi.beat.BeatModelInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by affo on 30/05/17.
 */
public class FXMLMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader controlsLoader = new FXMLLoader(getClass().getResource("/djview-controls.fxml"));
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/djview-view.fxml"));
        Scene controlsScene = new Scene(controlsLoader.load());
        Scene viewScene = new Scene(viewLoader.load());

        // Create a controller instance
        FXMLController controller = controlsLoader.getController();
        FXMLViewController viewController = viewLoader.getController();

        BeatModelInterface model = new BeatModel();
        model.init();
        controller.setModel(model);
        viewController.setModel(model);
        primaryStage.setScene(controlsScene);
        primaryStage.show();

        Stage viewStage = new Stage();
        viewStage.setScene(viewScene);
        viewStage.show();
    }
}