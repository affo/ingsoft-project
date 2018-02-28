package it.polimi.beat;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class BeatModel implements BeatModelInterface, Observer {
    private Beat beat;
    ArrayList<BeatObserver> beatObservers = new ArrayList<>();
    ArrayList<BPMObserver> bpmObservers = new ArrayList<>();

    public void init() {
        beat = new Beat(60);
        beat.addObserver(this);
        try {
            beat.init();
        } catch (Exception e) {
            System.err.println("Problem with beat");
            beat = null;
        }
    }

    public void on() {
        System.out.println("Drop the beat");
        beat.start();
        notifyBPMObservers();
    }

    public void off() {
        beat.stop();
        System.out.println("Stopped");
    }

    public void setBPM(int bpm) {
        beat.setBpm(bpm);
        notifyBPMObservers();
    }

    public int getBPM() {
        return beat.getBpm();
    }

    public void registerObserver(BeatObserver o) {
        beatObservers.add(o);
    }

    public void notifyBeatObservers() {
        for (int i = 0; i < beatObservers.size(); i++) {
            BeatObserver observer = beatObservers.get(i);
            observer.updateBeat();
        }
    }

    public void registerObserver(BPMObserver o) {
        bpmObservers.add(o);
    }

    public void notifyBPMObservers() {
        for (int i = 0; i < bpmObservers.size(); i++) {
            BPMObserver observer = bpmObservers.get(i);
            observer.updateBPM();
        }
    }

    // this is a beat event
    @Override
    public void update(Observable o, Object arg) {
        notifyBeatObservers();
    }
}
