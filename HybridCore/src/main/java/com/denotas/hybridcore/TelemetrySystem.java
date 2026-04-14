package com.denotas.hybridcore;

/**
 * Mede FPS e tempo de frame com buffer circular de float primitivos.
 * SEM autoboxing — evita GC pressure no Android.
 */
public class TelemetrySystem {

    private static final int WINDOW = 60;

    // Buffer circular (sem autoboxing Float)
    private final float[] frameTimes = new float[WINDOW];
    private int   writeIndex = 0;
    private int   count      = 0;

    private long  frameStart;
    private float avgFps;
    private float avgFrameMs;

    // ----------------------------------------------------------------

    public void beginFrame() {
        frameStart = System.nanoTime();
    }

    public void endFrame() {
        float ms = (System.nanoTime() - frameStart) / 1_000_000f;

        frameTimes[writeIndex] = ms;
        writeIndex = (writeIndex + 1) % WINDOW;
        if (count < WINDOW) count++;

        float sum = 0f;
        for (int i = 0; i < count; i++) sum += frameTimes[i];
        avgFrameMs = count > 0 ? sum / count : 0f;
        avgFps     = avgFrameMs > 0f ? 1000f / avgFrameMs : 0f;
    }

    // ----------------------------------------------------------------

    public float getAvgFps()     { return avgFps;     }
    public float getAvgFrameMs() { return avgFrameMs; }

    public int getUsedRamMB() {
        Runtime rt = Runtime.getRuntime();
        return (int)((rt.totalMemory() - rt.freeMemory()) / 1_048_576L);
    }

    public int getMaxRamMB() {
        return (int)(Runtime.getRuntime().maxMemory() / 1_048_576L);
    }
}
