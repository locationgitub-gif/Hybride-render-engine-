package com.denotas.hybridcore.render.backend;

import com.denotas.hybridcore.HybridCore;

/**
 * Backend Vulkan — stub para Mali-G52 MC2 (Vulkan 1.1.177).
 *
 * AVISOS DE SEGURANÇA (Mali-G52 / Vulkan 1.1):
 *  ❌ NÃO usar VK_KHR_buffer_device_address
 *  ❌ NÃO usar VK_EXT_descriptor_indexing
 *  ❌ NÃO usar VK_KHR_dynamic_rendering
 *  ❌ NÃO usar shaderFloat64
 *  ✅ Verificar mínimo Vulkan 1.1, NUNCA 1.2
 *  ✅ Sempre usar fence com timeout em vkWaitForFences
 *  ✅ MAILBOX ou FIFO — NUNCA IMMEDIATE sem verificação
 *
 * Esta implementação é um placeholder seguro.
 * A implementação completa requer LWJGL Vulkan bindings nativos.
 */
public class VulkanBackend implements IRenderBackend {

    @Override
    public void initialize() {
        throw new UnsupportedOperationException(
            "[VulkanBackend] Não implementado nesta versão. Use GLESBackend.");
    }

    @Override public void shutdown()    {}
    @Override public void beginFrame()  {}
    @Override public void endFrame()    {}

    @Override
    public String getName() { return "Vulkan 1.1 (stub)"; }

    @Override
    public boolean isAvailable() {
        // Stub — reporta indisponível até implementação completa
        return false;
    }
}
