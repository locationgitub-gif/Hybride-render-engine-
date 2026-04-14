package com.denotas.hybridcore.render.backend;

/**
 * Abstracção do backend de renderização.
 * Implementações: GLESBackend, VulkanBackend.
 */
public interface IRenderBackend {

    /** Inicializa o backend. Lança RuntimeException se não suportado. */
    void initialize();

    /** Liberta todos os recursos nativos. */
    void shutdown();

    /** Chamado no início de cada frame. */
    void beginFrame();

    /** Chamado no final de cada frame. */
    void endFrame();

    /** @return nome legível do backend (ex. "GLES 3.2", "Vulkan 1.1"). */
    String getName();

    /** @return true se o backend está disponível neste dispositivo. */
    boolean isAvailable();
}
