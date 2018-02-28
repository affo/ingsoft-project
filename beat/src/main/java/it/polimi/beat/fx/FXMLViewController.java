package it.polimi.beat.fx;

import it.polimi.beat.BPMObserver;
import it.polimi.beat.BeatModelInterface;
import it.polimi.beat.BeatObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by affo on 30/05/17.
 */
public class FXMLViewController implements BeatObserver, BPMObserver {
    private BeatModelInterface model;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label bpmOutput;

    public void setModel(BeatModelInterface model) {
        this.model = model;
        model.registerObserver((BPMObserver) this);
        model.registerObserver((BeatObserver) this);
    }

    @Override
    public void updateBPM() {
        int bpm = model.getBPM();
        bpmOutput.setText(String.valueOf(bpm));
    }

    @Override
    public void updateBeat() {
        progressBar.setProgress(1.0);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                progressBar.setProgress(0.0);
            }
        }, 100);
    }
}
