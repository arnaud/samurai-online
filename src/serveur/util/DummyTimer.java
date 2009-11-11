package serveur.util;

/*
* Created on Feb 22, 2006
*/

import com.jme.util.Timer;

/**
* DummyTimer représente un timer de base, qui est utilisé par le serveur dans un
* environnement ne gérant aucun affichage.
* @author Matthew D. Hicks
*/
public class DummyTimer extends Timer {
    private static final long TIMER_RESOLUTION = 1000000000;
   
    private long startTime;
    private long previousTime;
    private float tpf;
    private float fps;
   
    public DummyTimer() {
        startTime = System.nanoTime();
    }
   
    public long getTime() {
        return System.nanoTime() - startTime;
    }

    public long getResolution() {
        return TIMER_RESOLUTION;
    }

    public float getFrameRate() {
        return fps;
    }

    public float getTimePerFrame() {
        return tpf;
    }

    public void update() {
        tpf = (getTime() - previousTime) * (1.0f / TIMER_RESOLUTION);
        fps = 1.0f / tpf;
        previousTime = getTime();
    }
}