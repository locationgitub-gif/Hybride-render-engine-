package com.denotas.hybridcore.engine;

import com.denotas.hybridcore.HybridCore;
import com.denotas.hybridcore.config.HybridConfig;
import com.denotas.hybridcore.config.HybridConfig.RenderBackend;
import com.denotas.hybridcore.performance.AdaptiveQuality;
import com.denotas.hybridcore.performance.FrustumCuller;
import com.denotas.hybridcore.render.backend.GLESBackend;
import com.denotas.hybridcore.render.backend.IRenderBackend;
import com.denotas.hybridcore.render.backend.VulkanBackend;
import com.denotas.hybridcore.telemetry.TelemetrySystem;

/**
 * Motor central do HybridCore.
 * Selecciona e gere o backend de renderização activo.
 */
public class HybridEngine {

    private final HybridConfig    config;
    private final TelemetrySystem telemetry;
    private IRenderBackend        activeBackend;
    private AdaptiveQuality       adaptiveQuality;
    private FrustumCuller         frustumCuller;
    private boolean               running = false;

    // Informação de GPU detectada em runtime
    private String detectedGPU = "Desconhecido";

    public HybridEngine(HybridConfig config, TelemetrySystem telemetry) {
        this.config    = config;
        this.telemetry = telemetry;
    }

    public void initialize() {
        HybridCore.LOGGER.info("[HybridEngine] Seleccionando backend...");

        activeBackend = selectBackend();
        activeBackend.initialize();

        adaptiveQuality = new AdaptiveQuality(config, telemetry);
        frustumCuller   = new FrustumCuller();

        running = true;
        HybridCore.LOGGER.info("[HybridEngine] Backend activo: {}", activeBackend.getName());
    }

    public void shutdown() {
        if (activeBackend != null) activeBackend.shutdown();
        running = false;
    }

    public void beginFrame() {
        if (!running) return;
        telemetry.beginFrame();
        activeBackend.beginFrame();
    }

    public void endFrame() {
        if (!running) return;
        activeBackend.endFrame();
        telemetry.endFrame();
        if (config.isAdaptiveQuality()) adaptiveQuality.tick();
    }

    // -------------------------------------------------------

    private IRenderBackend selectBackend() {
        RenderBackend pref = config.getPreferredBackend();

        if (pref == RenderBackend.VULKAN) {
            VulkanBackend vk = new VulkanBackend();
            if (vk.isAvailable()) return vk;
            HybridCore.LOGGER.warn("[HybridEngine] Vulkan indisponível, fallback para GLES.");
        }

        // AUTO ou GLES: sempre usar GLES (seguro em todos os dispositivos Android)
        return new GLESBackend();
    }

    // -------------------------------------------------------
    // Getters para HUD

    public String          getActiveBackendName() { return activeBackend != null ? activeBackend.getName() : "N/A"; }
    public IRenderBackend  getActiveBackend()      { return activeBackend; }
    public FrustumCuller   getFrustumCuller()      { return frustumCuller; }
    public AdaptiveQuality getAdaptiveQuality()    { return adaptiveQuality; }
    public boolean         isRunning()             { return running; }

    public String getDetectedGPU()              { return detectedGPU; }
    public void   setDetectedGPU(String gpu)    { this.detectedGPU = gpu; }
}
