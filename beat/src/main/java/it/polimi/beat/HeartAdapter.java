package it.polimi.beat;

public class HeartAdapter implements BeatModelInterface {
    HeartModelInterface heart;

    public HeartAdapter(HeartModelInterface heart) {
        this.heart = heart;
    }

    public void init() {
    }

    public void on() {
    }

    public void off() {
    }

    public int getBPM() {
        return heart.getHeartRate();
    }

    public void setBPM(int bpm) {
    }

    public void registerObserver(BeatObserver o) {
        heart.registerObserver(o);
    }

    public void registerObserver(BPMObserver o) {
        heart.registerObserver(o);
    }
}
