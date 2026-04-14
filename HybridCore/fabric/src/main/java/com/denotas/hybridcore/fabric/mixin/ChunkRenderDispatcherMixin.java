package com.denotas.hybridcore.fabric.mixin;

import com.denotas.hybridcore.HybridCore;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.chunk.ChunkRendererRegionBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkBuilder.class)
public class ChunkRenderDispatcherMixin {

    @Inject(method = "rebuild", at = @At("HEAD"))
    private void hybridcore$onChunkRebuild(ChunkBuilder.BuiltChunk builtChunk, ChunkRendererRegionBuilder regionBuilder, CallbackInfo ci) {
        HybridCore hc = HybridCore.getInstance();
        if (hc != null && hc.getTelemetry() != null) {
            hc.getTelemetry().recordChunkRender();
        }
    }
}
