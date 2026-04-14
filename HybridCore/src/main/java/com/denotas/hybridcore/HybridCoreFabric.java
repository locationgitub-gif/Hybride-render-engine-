package com.denotas.hybridcore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HybridCoreFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HybridCore mod = new HybridCore();
        mod.init();

        // Registar HUD compacto
        HudRenderCallback.EVENT.register(new HybridHUD(mod));
    }
}
