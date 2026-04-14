package com.denotas.hybridcore.config;

/**
 * Configuração central do HybridCore.
 * Perfis ajustados para Mali-G52 MC2 (TBDR — Bifrost).
 */
public class HybridConfig {

    public enum RenderBackend { GLES, VULKAN, AUTO }

    // --- Geral ---
    private RenderBackend preferredBackend = RenderBackend.GLES;
    private int           renderDistance   = 8;
    private boolean       adaptiveQuality  = true;
    private boolean       debugOverlay     = false;
    private boolean       showHUD          = true;

    // --- Chunk ---
    private boolean greedyMeshing     = true;
    private boolean frustumCulling    = true;
    private boolean distanceCulling   = true;

    // --- GLES ---
    private boolean astcCompression   = true;
    private int     msaaSamples       = 4;
    private boolean anisotropicFilter = true;
    private int     anisotropyLevel   = 8;

    // --- Limites de qualidade ---
    private float targetFPS           = 30.0f;
    private int   minRenderDistance   = 4;
    private int   maxRenderDistance   = 12;

    // getters / setters
    public RenderBackend getPreferredBackend()          { return preferredBackend; }
    public void          setPreferredBackend(RenderBackend b) { this.preferredBackend = b; }

    public int     getRenderDistance()               { return renderDistance; }
    public void    setRenderDistance(int d)          { this.renderDistance = d; }

    public boolean isAdaptiveQuality()               { return adaptiveQuality; }
    public boolean isDebugOverlay()                  { return debugOverlay; }
    public void    setDebugOverlay(boolean v)        { this.debugOverlay = v; }

    public boolean isShowHUD()                       { return showHUD; }
    public void    setShowHUD(boolean v)             { this.showHUD = v; }

    public boolean isGreedyMeshing()                 { return greedyMeshing; }
    public boolean isFrustumCulling()                { return frustumCulling; }
    public boolean isDistanceCulling()               { return distanceCulling; }

    public boolean isAstcCompression()               { return astcCompression; }
    public int     getMsaaSamples()                  { return msaaSamples; }
    public boolean isAnisotropicFilter()             { return anisotropicFilter; }
    public int     getAnisotropyLevel()              { return anisotropyLevel; }

    public float   getTargetFPS()                    { return targetFPS; }
    public int     getMinRenderDistance()            { return minRenderDistance; }
    public int     getMaxRenderDistance()            { return maxRenderDistance; }
}
