package com.denotas.hybridcore.fabric.mixin;

import com.denotas.hybridcore.HybridCore;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    private static boolean gpuDetected = false;

    @Inject(method = "render", at = @At("HEAD"))
    private void hybridcore$onRenderHead(CallbackInfo ci) {
        HybridCore hc = HybridCore.getInstance();
        if (hc == null) return;

        // Detectar GPU uma única vez
        if (!gpuDetected) {
            detectGPU(hc);
            gpuDetected = true;
        }

        // Contar este draw call
        if (hc.getTelemetry() != null) {
            hc.getTelemetry().recordDrawCall();
        }
    }

    private void detectGPU(HybridCore hc) {
        try {
            Class<?> gl = Class.forName("org.lwjgl.opengl.GL11");
            java.lang.reflect.Method m = gl.getMethod("glGetString", int.class);
            Object renderer = m.invoke(null, 0x1F01 /* GL_RENDERER */);
            if (renderer != null) {
                hc.getEngine().setDetectedGPU(renderer.toString());
            }
        } catch (Exception e) {
            hc.getEngine().setDetectedGPU("Desconhecido");
        }
    }
}
