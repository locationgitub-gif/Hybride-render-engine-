package com.denotas.hybridcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HybridCore — Fase 1
 * Inicialização, telemetria de FPS e informação do dispositivo.
 */
public class HybridCore {

    public static final String MOD_ID      = "hybridcore";
    public static final String MOD_VERSION = "1.0.0";
    public static final Logger LOGGER      = LoggerFactory.getLogger(MOD_ID);

    private static HybridCore INSTANCE;

    private TelemetrySystem telemetry;

    // ----------------------------------------------------------------

    public static HybridCore getInstance() { return INSTANCE; }

    public void init() {
        INSTANCE  = this;
        telemetry = new TelemetrySystem();
        LOGGER.info("[HybridCore] v{} iniciado!", MOD_VERSION);
    }

    public TelemetrySystem getTelemetry() { return telemetry; }
}
