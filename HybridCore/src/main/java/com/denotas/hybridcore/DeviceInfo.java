package com.denotas.hybridcore;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Detecção de hardware em runtime.
 * Funciona em PojavLauncher (Android) e Desktop.
 * Detecção lazy — só executa com contexto GL activo.
 */
public final class DeviceInfo {

    // Resultados cached
    private static String  gpu            = "A detectar...";
    private static String  deviceModel    = "";
    private static String  androidVersion = "";
    private static String  arch           = System.getProperty("os.arch", "?");
    private static boolean android        = false;
    private static boolean detected       = false;

    private DeviceInfo() {}

    // ----------------------------------------------------------------

    /**
     * Executa detecção se ainda não foi feita.
     * Deve ser chamado com contexto GL activo (primeiro frame).
     */
    public static void detectIfNeeded() {
        if (detected) return;
        detected = true;

        // --- Android? ---
        try {
            Class<?> build = Class.forName("android.os.Build");
            android = true;

            String manufacturer = (String) build.getField("MANUFACTURER").get(null);
            String model        = (String) build.getField("MODEL").get(null);
            deviceModel = manufacturer + " " + model;

            Class<?> ver = Class.forName("android.os.Build$VERSION");
            androidVersion = "Android " + ver.getField("RELEASE").get(null)
                           + " (API " + ver.getField("SDK_INT").get(null) + ")";

        } catch (Exception e) {
            android        = false;
            deviceModel    = System.getProperty("os.name", "Desktop");
            androidVersion = System.getProperty("os.version", "");
        }

        // --- GPU via LWJGL (contexto GL obrigatório) ---
        try {
            Class<?> gl = Class.forName("org.lwjgl.opengl.GL11");
            Method   m  = gl.getMethod("glGetString", int.class);
            Object   r  = m.invoke(null, 0x1F01 /* GL_RENDERER */);
            if (r != null && !r.toString().isEmpty()) gpu = r.toString();
        } catch (Exception e) {
            gpu = "GLES (não detectado)";
        }

        HybridCore.LOGGER.info("[DeviceInfo] GPU={} | Device={} | {}",
            gpu, deviceModel, androidVersion);
    }

    // ----------------------------------------------------------------

    public static String  getGpu()            { return gpu;            }
    public static String  getDeviceModel()    { return deviceModel;    }
    public static String  getAndroidVersion() { return androidVersion; }
    public static String  getArch()           { return arch;           }
    public static boolean isAndroid()         { return android;        }
}
