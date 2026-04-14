package com.denotas.hybridcore.performance;

public class AdaptiveQuality {

    private static float performanceFactor = 1.0f; // 1.0 = ideal

    public static void update(float fps) {
        if (fps >= 60) {
            performanceFactor = 1.0f;
        } else if (fps >= 40) {
            performanceFactor = 0.75f;
        } else if (fps >= 25) {
            performanceFactor = 0.5f;
        } else {
            performanceFactor = 0.3f;
        }
    }

    public static float getPerformanceFactor() {
        return performanceFactor;
    }
}
