package it.polimi.beat.fx;

import it.polimi.beat.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by affo on 25/05/17.
 */
public class FXView extends Application implements BeatObserver, BPMObserver {
    private ControllerInterface ctrl;
    private BeatModelInterface model;

    private Label bpmOutputLabel;
    private ProgressBar beatBar;
    private MenuItem start, stop;

    @Override
    public void start(Stage primaryStage) throws Exception {
        createControls(primaryStage);
        createView(new Stage());
        model = new BeatModel();
        model.registerObserver((BPMObserver) this);
        model.registerObserver((BeatObserver) this);
        ctrl = new FXController(model, this);
    }

    private void createControls(Stage stage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        //grid.setPadding(new Insets(0, 10, 0, 10));
        //grid.setGridLinesVisible(true);

        // Menu
        MenuBar mb = new MenuBar();
        Menu controls = new Menu("Controls");
        start = new MenuItem("Start");
        start.setOnAction(event -> {
            System.out.println("Start clicked");
            ctrl.start();
        });
        stop = new MenuItem("Stop");
        stop.setOnAction(e -> {
            System.out.println("Stop clicked");
            ctrl.stop();
        });
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(e -> {
            System.out.println("Quit clicked");
            System.exit(0);
        });

        controls.getItems().addAll(start, stop, quit);
        mb.getMenus().add(controls);
        grid.add(mb, 0, 0, 2, 1);

        // BPM text area
        Label bpm = new Label("Enter BPM:");
        grid.add(bpm, 0, 1);

        TextField bpmText = new TextField();
        grid.add(bpmText, 1, 1);


        // Buttons
        Button btn = new Button();
        btn.setText("Set");
        btn.setOnAction(e -> {
            int bpms = Integer.parseInt(bpmText.getText());
            System.out.println("Setting BPMs to " + bpms);
            ctrl.setBPM(bpms);
        });
        // allow the button to grow
        btn.setMaxWidth(Double.MAX_VALUE);

        Button plus = new Button();
        plus.setText(">>");
        plus.setOnAction(e -> {
            System.out.println("Increasing BPMs");
            ctrl.increaseBPM();
        });
        plus.setMaxWidth(Double.MAX_VALUE);

        Button minus = new Button();
        minus.setText("<<");
        minus.setOnAction(e -> {
            System.out.println("Decreasing BPMs");
            ctrl.decreaseBPM();
        });
        minus.setMaxWidth(Double.MAX_VALUE);

        grid.add(btn, 0, 2, 2, 1);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(minus, plus);
        grid.add(hbox, 0, 3, 2, 1);

        GridPane.setMargin(btn, new Insets(5, 5, 5, 5));
        HBox.setMargin(plus, new Insets(5, 5, 5, 5));
        HBox.setMargin(minus, new Insets(5, 5, 5, 5));
        HBox.setHgrow(plus, Priority.ALWAYS);
        HBox.setHgrow(minus, Priority.ALWAYS);

        stage.setTitle("DJ Control");
        stage.setScene(new Scene(grid));
        stage.setResizable(false);
        stage.show();
    }

    private void createView(Stage stage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        beatBar = new ProgressBar(1.0);
        grid.add(beatBar, 0, 0, 2, 1);

        bpmOutputLabel = new Label("Offline");
        bpmOutputLabel.setTextAlignment(TextAlignment.CENTER);
        grid.add(bpmOutputLabel, 0, 1, 2, 1);

        stage.setTitle("View");
        stage.setScene(new Scene(grid));
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void updateBPM() {
        if (model != null) {
            int bpm = model.getBPM();
            if (bpm == 0) {
                if (bpmOutputLabel != null) {
                    bpmOutputLabel.setText("offline");
                }
            } else {
                if (bpmOutputLabel != null) {
                    bpmOutputLabel.setText("BPM: " + model.getBPM());
                }
            }
        }
    }

    @Override
    public void updateBeat() {
        if (beatBar != null) {
            beatBar.setProgress(1.0);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    beatBar.setProgress(0.0);
                }
            }, 100);
        }
    }

    public void enableStopMenuItem() {
        stop.setDisable(false);
    }

    public void disableStopMenuItem() {
        stop.setDisable(true);
    }

    public void enableStartMenuItem() {
        start.setDisable(false);
    }

    public void disableStartMenuItem() {
        start.setDisable(true);
    }
}
