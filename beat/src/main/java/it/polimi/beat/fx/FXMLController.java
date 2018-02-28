package it.polimi.beat.fx;

import it.polimi.beat.BeatModelInterface;
import it.polimi.beat.ControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

/**
 * Created by affo on 30/05/17.
 */
public class FXMLController implements ControllerInterface {
    private BeatModelInterface model;

    @FXML
    private TextField bpmText;
    @FXML
    private MenuItem startItem;
    @FXML
    private MenuItem stopItem;

    public void setModel(BeatModelInterface model) {
        this.model = model;
    }

    @Override
    @FXML
    public void start() {
        model.on();
        startItem.setDisable(true);
        stopItem.setDisable(false);
    }

    @Override
    @FXML
    public void stop() {
        model.off();
        startItem.setDisable(false);
        stopItem.setDisable(true);
    }

    @FXML
    public void increaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm + 1);
    }

    @FXML
    public void decreaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm - 1);
    }

    @Override
    public void setBPM(int bpm) {
        model.setBPM(bpm);
    }

    @FXML
    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void setBPM(ActionEvent actionEvent) {
        setBPM(Integer.parseInt(bpmText.getText()));
    }
}
