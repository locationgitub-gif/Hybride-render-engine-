package com.denotas.hybridcore.fabric;

import com.denotas.hybridcore.HybridCore;
import com.denotas.hybridcore.gui.HybridHUD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HybridCoreFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HybridCore mod = new HybridCore();
        mod.init();

        // Registar HUD
        HudRenderCallback.EVENT.register(new HybridHUD(mod));
    }
}
