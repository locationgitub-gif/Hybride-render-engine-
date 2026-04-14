package com.denotas.hybridcore.mixin;

import com.denotas.hybridcore.DeviceInfo;
import com.denotas.hybridcore.HybridCore;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Intercepta o loop de render principal para:
 *  1. Medir FPS e tempo de frame
 *  2. Detectar GPU no primeiro frame (contexto GL já activo)
 */
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    private static boolean firstFrame = true;

    @Inject(method = "render", at = @At("HEAD"))
    private void hybridcore$beginFrame(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        HybridCore hc = HybridCore.getInstance();
        if (hc == null) return;

        // Detectar GPU apenas no primeiro frame — contexto GL já está activo
        if (firstFrame) {
            DeviceInfo.detectIfNeeded();
            firstFrame = false;
        }

        hc.getTelemetry().beginFrame();
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void hybridcore$endFrame(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
        HybridCore hc = HybridCore.getInstance();
        if (hc != null) hc.getTelemetry().endFrame();
    }
}
