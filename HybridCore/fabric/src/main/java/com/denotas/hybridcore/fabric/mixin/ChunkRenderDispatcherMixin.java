package com.denotas.hybridcore.fabric.mixin;

import com.denotas.hybridcore.HybridCore;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.render.chunk.ChunkRendererRegionBuilder;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.denotas.hybridcore.performance.scheduler.SmartChunkScheduler;

@Mixin(ChunkBuilder.class)
public class ChunkRenderDispatcherMixin {

    @Inject(method = "rebuild", at = @At("HEAD"), cancellable = true)
    private void hybridcore$onChunkRebuild(
            ChunkBuilder.BuiltChunk builtChunk,
            ChunkRendererRegionBuilder regionBuilder,
            CallbackInfo ci
    ) {
        HybridCore hc = HybridCore.getInstance();

        if (hc != null && hc.getTelemetry() != null) {
            hc.getTelemetry().recordChunkRender();
        }

        // 🧠 NOVO: extrair posição do chunk
        ChunkPos pos = builtChunk.getChunkPos();

        SmartChunkScheduler scheduler = SmartChunkScheduler.getInstance();

        // 🚨 DECISÃO DO SCHEDULER
        if (!scheduler.shouldRebuildNow(pos)) {
            scheduler.queueForLater(() -> {
                // re-executa o rebuild depois
                builtChunk.rebuild(regionBuilder);
            }, pos);

            ci.cancel(); // cancela execução atual
        }
    }
}
