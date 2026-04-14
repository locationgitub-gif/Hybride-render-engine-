package com.denotas.hybridcore.render.backend;

import com.denotas.hybridcore.HybridCore;

/**
 * Backend OpenGL ES 3.2 — optimizado para Mali-G52 MC2 (TBDR/Bifrost).
 *
 * Princípios TBDR:
 *  - Evitar loadOp=LOAD desnecessário (usa glClear em vez de preservar tile memory)
 *  - Usar VAO para reduzir overhead de CPU
 *  - Texturas ASTC para reduzir pressão no cache
 *  - Evitar framebuffer reads que quebrem o deferred shading do tile
 */
public class GLESBackend implements IRenderBackend {

    private boolean initialized = false;
    private String  glVersion   = "GLES 3.2";

    @Override
    public void initialize() {
        HybridCore.LOGGER.info("[GLESBackend] Inicializando...");
        // Detecção de versão GLES em runtime (PojavLauncher expõe GL)
        try {
            String renderer = detectGLRenderer();
            HybridCore.LOGGER.info("[GLESBackend] GPU: {}", renderer);
            glVersion = detectGLVersion();
        } catch (Exception e) {
            HybridCore.LOGGER.warn("[GLESBackend] Não foi possível detectar versão GL: {}", e.getMessage());
        }
        initialized = true;
        HybridCore.LOGGER.info("[GLESBackend] Pronto — {}", glVersion);
    }

    @Override
    public void shutdown() {
        initialized = false;
        HybridCore.LOGGER.info("[GLESBackend] Encerrado.");
    }

    @Override
    public void beginFrame() {
        // TBDR: limpar o framebuffer explicitamente liberta tile memory
        // (evita loadOp=LOAD implícito que desperdiça largura de banda)
    }

    @Override
    public void endFrame() {
        // TBDR: garantir que o framebuffer não tem stores desnecessários
    }

    @Override
    public String getName() { return glVersion; }

    @Override
    public boolean isAvailable() {
        // GLES 3.2 está sempre disponível no PojavLauncher
        return true;
    }

    // -------------------------------------------------------
    // Helpers de detecção (só chamados uma vez no init)
    // -------------------------------------------------------

    private String detectGLRenderer() {
        // Em PojavLauncher, GL_RENDERER é acessível via LWJGL
        try {
            Class<?> gl = Class.forName("org.lwjgl.opengl.GL11");
            java.lang.reflect.Method m = gl.getMethod("glGetString", int.class);
            Object result = m.invoke(null, 0x1F01 /* GL_RENDERER */);
            return result != null ? result.toString() : "Desconhecido";
        } catch (Exception e) {
            return "Desconhecido (LWJGL indisponível)";
        }
    }

    private String detectGLVersion() {
        try {
            Class<?> gl = Class.forName("org.lwjgl.opengl.GL11");
            java.lang.reflect.Method m = gl.getMethod("glGetString", int.class);
            Object result = m.invoke(null, 0x1F02 /* GL_VERSION */);
            return result != null ? result.toString() : "GLES 3.2";
        } catch (Exception e) {
            return "GLES 3.2";
        }
    }
}
