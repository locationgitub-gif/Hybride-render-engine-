package com.denotas.hybridcore.mixin;

import com.denotas.hybridcore.DeviceInfo;
import com.denotas.hybridcore.HybridCore;
import com.denotas.hybridcore.TelemetrySystem;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Adiciona informações do HybridCore e do dispositivo ao painel
 * esquerdo do ecrã de debug (F3).
 *
 * Aparece no final da lista, separado por uma linha vazia.
 */
@Mixin(DebugHud.class)
public class DebugHudMixin {

    @Inject(method = "getLeftText", at = @At("RETURN"))
    private void hybridcore$addDebugLines(CallbackInfoReturnable<List<String>> cir) {
        HybridCore hc = HybridCore.getInstance();
        if (hc == null) return;

        List<String>    list = cir.getReturnValue();
        TelemetrySystem tele = hc.getTelemetry();

        float fps    = tele.getAvgFps();
        float ms     = tele.getAvgFrameMs();
        int   usedMB = tele.getUsedRamMB();
        int   maxMB  = tele.getMaxRamMB();

        // Cor de FPS com código § do Minecraft
        String fpsColor = fps < 20f ? "§c" : fps < 28f ? "§e" : "§a";

        list.add("");   // separador visual
        list.add("§6§l[HybridCore v" + HybridCore.MOD_VERSION + "] §r§aACTIVO ✔");
        list.add("§7Backend: §fGLES 3.2");
        list.add("§7FPS: " + fpsColor + (int)fps + " §7| §7Frame: §f" + String.format("%.1f ms", ms));
        list.add("§7RAM: §f" + usedMB + " MB §7/ §f" + maxMB + " MB");
        list.add("");   // separador
        list.add("§e§l[Dispositivo]");
        list.add("§7Modelo: §f"   + DeviceInfo.getDeviceModel());
        list.add("§7Sistema: §f"  + DeviceInfo.getAndroidVersion());
        list.add("§7Arch: §f"     + DeviceInfo.getArch());
        list.add("§7GPU: §f"      + DeviceInfo.getGpu());
        list.add("§7Plataforma: §f" + (DeviceInfo.isAndroid() ? "Android/PojavLauncher" : "Desktop"));
    }
}
