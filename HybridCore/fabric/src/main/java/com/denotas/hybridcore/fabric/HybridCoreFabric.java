package com.denotas.hybridcore;

import net.fabricmc.api.ClientModInitializer;

public class HybridCoreFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new HybridCore();
        HybridCore.LOGGER.info("HybridCore iniciado com sucesso");
    }
}
