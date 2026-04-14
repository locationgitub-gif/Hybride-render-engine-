# HybridCore — Motor de Renderização para Android

Mod Fabric para Minecraft 1.21.1 que substitui o pipeline de renderização padrão por um motor optimizado para Android, especificamente para o **TECNO SPARK 9 Pro (Mali-G52 MC2)**.

---

## Hardware Alvo

| Campo | Valor |
|---|---|
| GPU | Mali-G52 MC2 (Bifrost TBDR) |
| Vulkan | 1.1.177 |
| OpenGL ES | 3.2 |
| Android | 12 (API 31) |
| RAM | ~4 GB |

---

## HUD em Jogo

O HybridCore exibe um painel no canto superior esquerdo com:

- `FPS` e `Frame Time (ms)` — cor verde/amarelo/vermelho conforme performance
- `Backend activo` — GLES 3.2 ou Vulkan 1.1
- `GPU detectada` — nome real da GPU em runtime
- `Memória` usada / disponível (MB)
- `Render Distance` actual
- `Chunks renderizados` no frame
- `Draw Calls` no frame
- `Qualidade Adaptativa` — ACTIVA (reduzindo RD) ou OK

---

## Build

```bash
# Requer Java 21
./gradlew build
```

JAR produzido em: `fabric/build/libs/hybridcore-1.0.0.jar`

---

## Instalação no PojavLauncher

1. Copiar o JAR para a pasta `mods/` do perfil Fabric 1.21.1
2. Lançar Minecraft

---

## Arquitectura

```
HybridCore (entry point)
├── HybridEngine          ← selecciona e gere o backend
│   ├── GLESBackend       ← GLES 3.2, optimizado TBDR
│   └── VulkanBackend     ← stub (Vulkan 1.1, fase 2)
├── TelemetrySystem       ← FPS, frame time, memória
├── AdaptiveQuality       ← ajuste dinâmico de render distance
├── FrustumCuller         ← culling software (Mali não suporta HW)
└── HybridHUD             ← painel de métricas em jogo
```

---

## Limitações Conhecidas — Mali-G52

| Funcionalidade | Estado |
|---|---|
| `VK_KHR_buffer_device_address` | ❌ Não suportado — causa crash |
| `shaderFloat64` | ❌ Não suportado — causa crash |
| `VK_EXT_descriptor_indexing` | ❌ Não suportado |
| `VK_KHR_dynamic_rendering` | ❌ Não suportado |
| Clip/Cull Distance HW | ❌ — usar FrustumCuller por software |
| ASTC Compression | ✅ Suportado — usar para texturas |
| Vulkan 1.1 | ✅ Disponível (1.1.177) |
| Compute Shaders | ✅ Disponível |

---

## Próximos Passos

- [ ] Implementar VulkanBackend completo (LWJGL + VK_KHR_android_surface)
- [ ] Greedy meshing para chunks
- [ ] ASTC texture pipeline
- [ ] Detector térmico Android (throttle antes de aquecimento)
