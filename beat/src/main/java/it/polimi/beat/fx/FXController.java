package it.polimi.beat.fx;

import it.polimi.beat.BeatModelInterface;
import it.polimi.beat.ControllerInterface;
import it.polimi.beat.fx.FXView;

/**
 * Created by affo on 25/05/17.
 */
public class FXController implements ControllerInterface {
    BeatModelInterface model;
    FXView view;

    public FXController(BeatModelInterface model, FXView view) {
        this.model = model;
        this.view = view;
        view.disableStopMenuItem();
        view.enableStartMenuItem();
        model.init();
    }

    public void start() {
        model.on();
        view.disableStartMenuItem();
        view.enableStopMenuItem();
    }

    public void stop() {
        model.off();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
    }

    public void increaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm + 1);
    }

    public void decreaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm - 1);
    }

    public void setBPM(int bpm) {
        model.setBPM(bpm);
    }
}
