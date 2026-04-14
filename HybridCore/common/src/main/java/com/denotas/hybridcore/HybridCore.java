package com.denotas.hybridcore;

import com.denotas.hybridcore.config.HybridConfig;
import com.denotas.hybridcore.engine.HybridEngine;
import com.denotas.hybridcore.telemetry.TelemetrySystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HybridCore {

    public static final String MOD_ID = "hybridcore";
    public static final String MOD_VERSION = "1.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static HybridCore instance;
    private HybridEngine engine;
    private TelemetrySystem telemetry;
    private HybridConfig config;

    public static HybridCore getInstance() {
        return instance;
    }

    public void init() {
        instance = this;
        LOGGER.info("[HybridCore] Iniciando v{}", MOD_VERSION);

        config    = new HybridConfig();
        telemetry = new TelemetrySystem();
        engine    = new HybridEngine(config, telemetry);
        engine.initialize();

        LOGGER.info("[HybridCore] Backend activo: {}", engine.getActiveBackendName());
    }

    public HybridEngine    getEngine()    { return engine;    }
    public TelemetrySystem getTelemetry() { return telemetry; }
    public HybridConfig    getConfig()    { return config;    }
}
