package com.denotas.hybridcore;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * HUD compacto — sempre visível no jogo (excepto menus e F3).
 *
 * Linha 1: [HC v1.0.0] GLES 3.2
 * Linha 2: FPS: 60 | Frame: 16.7 ms
 *
 * Quando F3 está activo: este HUD esconde-se automaticamente
 * e o DebugHudMixin adiciona as informações completas ao painel de debug.
 */
public class HybridHUD implements HudRenderCallback {

    private static final int COLOR_GOLD  = 0xFFFFAA00;
    private static final int COLOR_GREEN = 0xFF55FF55;
    private static final int COLOR_WARN  = 0xFFFFFF55;
    private static final int COLOR_CRIT  = 0xFFFF5555;
    private static final int COLOR_GRAY  = 0xFFAAAAAA;

    private final HybridCore mod;

    public HybridHUD(HybridCore mod) {
        this.mod = mod;
    }

    // ----------------------------------------------------------------

    @Override
    public void onHudRender(DrawContext ctx, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();

        // Não renderizar em menus ou fora do jogo
        if (mc.world == null || mc.currentScreen != null) return;

        // Quando F3 está activo, o DebugHudMixin já mostra tudo — esconder este HUD
        if (mc.inGameHud != null && mc.inGameHud.getDebugHud().shouldShowDebugHud()) return;

        TelemetrySystem tele = mod.getTelemetry();
        TextRenderer    tr   = mc.textRenderer;

        float fps  = tele.getAvgFps();
        float ms   = tele.getAvgFrameMs();

        // Cor do FPS
        int fpsColor = fps < 20f ? COLOR_CRIT : fps < 28f ? COLOR_WARN : COLOR_GREEN;

        // Linha 1: título fixo
        Text title = Text.literal("[HC v" + HybridCore.MOD_VERSION + "] ")
                         .formatted(Formatting.GOLD)
                         .append(Text.literal("GLES 3.2").formatted(Formatting.GRAY));

        // Linha 2: FPS dinâmico
        String fpsStr = String.format("FPS: %d  |  %.1f ms", (int)fps, ms);
        Text   fps2   = Text.literal("FPS: ").formatted(Formatting.GRAY)
                            .append(Text.literal(String.valueOf((int)fps))
                                        .withColor(fpsColor))
                            .append(Text.literal(String.format("  |  %.1f ms", ms))
                                        .formatted(Formatting.GRAY));

        ctx.drawTextWithShadow(tr, title, 4, 4,  0xFFFFFF);
        ctx.drawTextWithShadow(tr, fps2,  4, 14, 0xFFFFFF);
    }
}
