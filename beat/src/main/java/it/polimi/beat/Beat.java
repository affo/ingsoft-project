package it.polimi.beat;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by affo on 25/05/17.
 */
public class Beat extends Observable {
    private int bpm;
    private Timer timer;
    private final String bipFilePath = "/bip.wav";
    private Clip clip;


    public Beat(int bpm) {
        this.bpm = bpm;
    }

    public void init() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(bipFilePath));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
        long ms = fromBpmToMs(bpm);

        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clip.stop();
                clip.setFramePosition(0);
                setChanged();
                notifyObservers();
                clip.start();
            }
        }, ms, ms);
    }

    public int getBpm() {
        return bpm;
    }

    private long fromBpmToMs(int bpm) {
        return (long) ((60f / bpm) * 1000);
    }

    public void start() {
        setBpm(this.bpm);
    }

    public void stop() {
        timer.cancel();
    }
}
