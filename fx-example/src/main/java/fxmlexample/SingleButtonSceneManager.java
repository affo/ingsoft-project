package fxmlexample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.function.Function;

/**
 * This class manages a Scene that can switch to another one:
 *  - it creates the scene
 *  - it implements onAction listeners
 *  - it is Observable because it notifies when the switch has been triggered
 */
public class SingleButtonSceneManager extends Observable implements EventHandler<ActionEvent> {
    private final String name;
    private final Function<Integer, Integer> function;
    private int n = 1;

    private Text value;
    private Button button, goTo, save;

    private final String cssUrl = getClass().getResource("/style.css").toExternalForm();

    public SingleButtonSceneManager(String name, Function<Integer, Integer> function) {
        this.name = name;
        this.function = function;
    }

    private void refreshText() {
        value.setText(">>> " + n + " <<<");
    }

    public Scene create() {
        VBox vbox = new VBox(10);
        vbox.setPrefHeight(100);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        value = new Text();
        value.getStyleClass().add("green"); // for CSS
        refreshText();
        button = new Button("Action");
        goTo = new Button(">>");
        save = new Button("Save");

        HBox hbox = new HBox(10, button, goTo);

        vbox.getChildren().addAll(value, hbox, save);

        button.setOnAction(e -> {
            n = function.apply(n);
            refreshText();
        });

        // we can provide a brand new anonymous EventHandler
        goTo.setOnAction(e -> {
            setChanged();
            notifyObservers();
        });

        // or, this object can implement EventHandler
        save.setOnAction(this);

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add(cssUrl); // add the CSS

        return scene;
    }

    /**
     * When the "Save" button is clicked, a new frame containing the current data is created.
     * It holds a snapshot of the current state.
     * @param event
     */
    @Override
    public void handle(ActionEvent event) {
        try {
            /* // In this way we cannot init the data in the controller!
            Parent root = FXMLLoader.load(getClass().getResource("/snapshot.fxml"));
            Stage stage = new Stage();
            stage.setTitle(name);
            stage.setScene(new Scene(root));
            stage.show();
            */

            // This procedure is to get the controller reference and init its data
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/snapshot.fxml"));
            Parent root = fxmlLoader.load();
            SnapshotClientSideController clientSideController = fxmlLoader.getController();
            clientSideController.initData(n);
            Stage stage = new Stage();
            stage.setTitle(name);
            Scene mainScene = new Scene(root);

            // we can also add a stylesheet to the scene
            mainScene.getStylesheets().add(cssUrl);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error on loading frame: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
