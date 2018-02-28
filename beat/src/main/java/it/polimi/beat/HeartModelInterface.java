package it.polimi.beat;

public interface HeartModelInterface {
    int getHeartRate();

    void registerObserver(BeatObserver o);

    void registerObserver(BPMObserver o);
}
