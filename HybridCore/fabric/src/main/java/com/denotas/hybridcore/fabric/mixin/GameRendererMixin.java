package com.denotas.hybridcore.fabric.mixin;

import com.denotas.hybridcore.HybridCore;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void hybridcore$beginFrame(CallbackInfo ci) {
        HybridCore hc = HybridCore.getInstance();
        if (hc != null && hc.getEngine() != null) {
            hc.getEngine().beginFrame();
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void hybridcore$endFrame(CallbackInfo ci) {
        HybridCore hc = HybridCore.getInstance();
        if (hc != null && hc.getEngine() != null) {
            hc.getEngine().endFrame();
        }
    }
}
