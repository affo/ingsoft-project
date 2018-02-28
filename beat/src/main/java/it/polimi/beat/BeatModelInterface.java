package it.polimi.beat;

public interface BeatModelInterface {
    void init();

    void on();

    void off();

    void setBPM(int bpm);

    int getBPM();

    void registerObserver(BeatObserver o);

    void registerObserver(BPMObserver o);
}
