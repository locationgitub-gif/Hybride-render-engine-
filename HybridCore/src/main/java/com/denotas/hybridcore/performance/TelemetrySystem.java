package com.denotas.hybridcore.performance;

public class TelemetrySystem {

    private long lastTime = System.nanoTime();
    private float fps = 0f;

    private int chunkRenders = 0;

    public void beginFrame() {
        long now = System.nanoTime();
        float delta = (now - lastTime) / 1_000_000_000f;
        lastTime = now;

        if (delta > 0) {
            fps = 1f / delta;
        }

        // Atualiza AdaptiveQuality automaticamente
        AdaptiveQuality.update(fps);

        // reset contadores por frame
        chunkRenders = 0;
    }

    public float getFps() {
        return fps;
    }

    // ✅ AGORA EXISTE (fix do teu erro)
    public void recordChunkRender() {
        chunkRenders++;
    }

    public int getChunkRenders() {
        return chunkRenders;
    }
}
