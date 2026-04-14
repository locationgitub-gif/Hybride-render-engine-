package com.denotas.hybridcore.gui;

import com.denotas.hybridcore.HybridCore;
import com.denotas.hybridcore.config.HybridConfig;
import com.denotas.hybridcore.engine.HybridEngine;
import com.denotas.hybridcore.performance.AdaptiveQuality;
import com.denotas.hybridcore.telemetry.TelemetrySystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

/**
 * HUD do HybridCore — exibido no canto superior esquerdo.
 *
 * Linha 1:  [HybridCore v1.0.0]
 * Linha 2:  FPS: 60  |  Frame: 16.6 ms
 * Linha 3:  Backend: GLES 3.2
 * Linha 4:  GPU: Mali-G52 MC2
 * Linha 5:  Memória: 512 MB / 2048 MB
 * Linha 6:  Render Distance: 8
 * Linha 7:  Chunks: 42
 * Linha 8:  Draw Calls: 128
 * Linha 9:  Qualidade Adaptativa: ACTIVA / OK
 */
public class HybridHUD implements HudRenderCallback {

    private static final int PADDING    = 4;
    private static final int LINE_H     = 10;
    private static final int BG_COLOR   = 0x88000000; // preto semi-transparente
    private static final int COLOR_TITLE = 0xFFFFAA00; // laranja
    private static final int COLOR_OK    = 0xFF55FF55; // verde
    private static final int COLOR_WARN  = 0xFFFFFF55; // amarelo
    private static final int COLOR_CRIT  = 0xFFFF5555; // vermelho
    private static final int COLOR_INFO  = 0xFFCCCCCC; // cinzento claro

    private final HybridCore mod;

    public HybridHUD(HybridCore mod) {
        this.mod = mod;
    }

    @Override
    public void onHudRender(DrawContext ctx, RenderTickCounter tickCounter) {
        if (mod == null) return;
        HybridConfig    cfg  = mod.getConfig();
        if (!cfg.isShowHUD()) return;

        TelemetrySystem tele = mod.getTelemetry();
        HybridEngine    eng  = mod.getEngine();

        MinecraftClient mc   = MinecraftClient.getInstance();
        TextRenderer    tr   = mc.textRenderer;

        // --- Recolher dados ---
        float fps         = tele.getAvgFPS();
        float frameMs     = tele.getAvgFrameTimeMs();
        String backend    = eng.getActiveBackendName();
        String gpu        = eng.getDetectedGPU();
        int usedMB        = tele.getUsedMemoryMB();
        int maxMB         = tele.getMaxMemoryMB();
        int rd            = cfg.getRenderDistance();
        int chunks        = tele.getLastChunksRendered();
        int drawCalls     = tele.getLastDrawCalls();
        boolean throttle  = eng.getAdaptiveQuality() != null && eng.getAdaptiveQuality().isThrottling();

        // --- Linhas ---
        String[] lines = {
            "HybridCore v" + HybridCore.MOD_VERSION,
            String.format("FPS: %d  |  Frame: %.1f ms", (int)fps, frameMs),
            "Backend: " + backend,
            "GPU: " + gpu,
            String.format("Memória: %d MB / %d MB", usedMB, maxMB),
            "Render Distance: " + rd,
            "Chunks: " + chunks,
            "Draw Calls: " + drawCalls,
            "Qualidade Adaptativa: " + (throttle ? "ACTIVA" : "OK")
        };

        // --- Calcular largura do painel ---
        int maxW = 0;
        for (String l : lines) maxW = Math.max(maxW, tr.getWidth(l));
        int panelW = maxW + PADDING * 2;
        int panelH = lines.length * LINE_H + PADDING * 2;

        // Fundo semitransparente
        ctx.fill(0, 0, panelW, panelH, BG_COLOR);

        // Desenhar linhas
        for (int i = 0; i < lines.length; i++) {
            int color = COLOR_INFO;
            if (i == 0)                         color = COLOR_TITLE;
            else if (i == 1 && fps < 20f)       color = COLOR_CRIT;
            else if (i == 1 && fps < 28f)       color = COLOR_WARN;
            else if (i == 1)                    color = COLOR_OK;
            else if (i == 4 && usedMB > maxMB * 0.85f) color = COLOR_WARN;
            else if (i == 8 && throttle)        color = COLOR_WARN;

            ctx.drawText(tr, lines[i], PADDING, PADDING + i * LINE_H, color, false);
        }
    }
}
