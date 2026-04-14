package com.denotas.hybridcore.performance;

import com.denotas.hybridcore.HybridCore;
import com.denotas.hybridcore.config.HybridConfig;
import com.denotas.hybridcore.telemetry.TelemetrySystem;

/**
 * Ajusta a render distance dinamicamente para manter o FPS alvo.
 * Verifica a cada 60 frames (≈2s a 30fps).
 */
public class AdaptiveQuality {

    private static final int CHECK_INTERVAL = 60;

    private final HybridConfig    config;
    private final TelemetrySystem telemetry;
    private int                   frameCounter = 0;
    private boolean               active       = false;

    public AdaptiveQuality(HybridConfig config, TelemetrySystem telemetry) {
        this.config    = config;
        this.telemetry = telemetry;
    }

    public void tick() {
        frameCounter++;
        if (frameCounter < CHECK_INTERVAL) return;
        frameCounter = 0;

        float fps    = telemetry.getAvgFPS();
        float target = config.getTargetFPS();
        int   cur    = config.getRenderDistance();

        if (fps < target * 0.85f && cur > config.getMinRenderDistance()) {
            config.setRenderDistance(cur - 1);
            active = true;
            HybridCore.LOGGER.debug("[AdaptiveQuality] FPS={} → RD {}", (int)fps, config.getRenderDistance());
        } else if (fps > target * 1.15f && cur < config.getMaxRenderDistance()) {
            config.setRenderDistance(cur + 1);
            HybridCore.LOGGER.debug("[AdaptiveQuality] FPS={} → RD {}", (int)fps, config.getRenderDistance());
        } else {
            active = false;
        }
    }

    /** @return true se a qualidade está actualmente a ser reduzida. */
    public boolean isThrottling() { return active; }
}
