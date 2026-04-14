package com.denotas.hybridcore.telemetry;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Sistema de telemetria de baixo overhead.
 * Mantém janela deslizante de 60 frames para médias suaves.
 */
public class TelemetrySystem {

    private static final int WINDOW = 60;

    // Frame timing
    private long frameStart;
    private final Deque<Float> frameTimes = new ArrayDeque<>(WINDOW);

    // Contadores por frame
    private int drawCallsThisFrame;
    private int chunksRenderedThisFrame;

    // Valores publicados (lidos pelo HUD)
    private float avgFPS;
    private float avgFrameTimeMs;
    private int   lastDrawCalls;
    private int   lastChunksRendered;
    private long  usedMemoryBytes;
    private long  maxMemoryBytes;

    public void beginFrame() {
        frameStart = System.nanoTime();
        drawCallsThisFrame    = 0;
        chunksRenderedThisFrame = 0;
    }

    public void endFrame() {
        float ms = (System.nanoTime() - frameStart) / 1_000_000f;

        if (frameTimes.size() >= WINDOW) frameTimes.poll();
        frameTimes.offer(ms);

        float sum = 0f;
        for (float t : frameTimes) sum += t;
        avgFrameTimeMs = sum / frameTimes.size();
        avgFPS         = avgFrameTimeMs > 0 ? 1000f / avgFrameTimeMs : 0f;

        lastDrawCalls      = drawCallsThisFrame;
        lastChunksRendered = chunksRenderedThisFrame;

        Runtime rt = Runtime.getRuntime();
        usedMemoryBytes = rt.totalMemory() - rt.freeMemory();
        maxMemoryBytes  = rt.maxMemory();
    }

    public void recordDrawCall()    { drawCallsThisFrame++; }
    public void recordChunkRender() { chunksRenderedThisFrame++; }

    // Leituras
    public float getAvgFPS()            { return avgFPS; }
    public float getAvgFrameTimeMs()    { return avgFrameTimeMs; }
    public int   getLastDrawCalls()     { return lastDrawCalls; }
    public int   getLastChunksRendered(){ return lastChunksRendered; }
    public long  getUsedMemoryBytes()   { return usedMemoryBytes; }
    public long  getMaxMemoryBytes()    { return maxMemoryBytes; }

    /** Memória usada em MB (para exibição). */
    public int getUsedMemoryMB()  { return (int)(usedMemoryBytes / 1_048_576L); }
    /** Memória máxima em MB (para exibição). */
    public int getMaxMemoryMB()   { return (int)(maxMemoryBytes  / 1_048_576L); }
}
