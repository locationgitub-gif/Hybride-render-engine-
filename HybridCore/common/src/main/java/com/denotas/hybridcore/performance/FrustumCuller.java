package com.denotas.hybridcore.performance;

/**
 * Frustum culler simplificado para chunks.
 * O Mali-G52 não suporta Clip/Cull Distance nativo —
 * todo o culling deve ser feito por software (CPU).
 */
public class FrustumCuller {

    // 6 planos do frustum: [normal.x, normal.y, normal.z, d]
    private final float[][] planes = new float[6][4];

    /**
     * Actualiza os planos do frustum a partir da matriz de projecção+view.
     * Método de Gribb/Hartmann: extrai planos directamente da MVP.
     *
     * @param mvp array[16] da matriz MVP (column-major, igual ao OpenGL)
     */
    public void update(float[] mvp) {
        // Left
        planes[0][0] = mvp[3]  + mvp[0];
        planes[0][1] = mvp[7]  + mvp[4];
        planes[0][2] = mvp[11] + mvp[8];
        planes[0][3] = mvp[15] + mvp[12];
        // Right
        planes[1][0] = mvp[3]  - mvp[0];
        planes[1][1] = mvp[7]  - mvp[4];
        planes[1][2] = mvp[11] - mvp[8];
        planes[1][3] = mvp[15] - mvp[12];
        // Bottom
        planes[2][0] = mvp[3]  + mvp[1];
        planes[2][1] = mvp[7]  + mvp[5];
        planes[2][2] = mvp[11] + mvp[9];
        planes[2][3] = mvp[15] + mvp[13];
        // Top
        planes[3][0] = mvp[3]  - mvp[1];
        planes[3][1] = mvp[7]  - mvp[5];
        planes[3][2] = mvp[11] - mvp[9];
        planes[3][3] = mvp[15] - mvp[13];
        // Near
        planes[4][0] = mvp[3]  + mvp[2];
        planes[4][1] = mvp[7]  + mvp[6];
        planes[4][2] = mvp[11] + mvp[10];
        planes[4][3] = mvp[15] + mvp[14];
        // Far
        planes[5][0] = mvp[3]  - mvp[2];
        planes[5][1] = mvp[7]  - mvp[6];
        planes[5][2] = mvp[11] - mvp[10];
        planes[5][3] = mvp[15] - mvp[14];

        for (float[] p : planes) normalize(p);
    }

    /**
     * Testa uma AABB (Axis-Aligned Bounding Box) contra o frustum.
     *
     * @return true se a AABB é totalmente fora do frustum (deve ser descartada)
     */
    public boolean isOutside(float minX, float minY, float minZ,
                             float maxX, float maxY, float maxZ) {
        for (float[] p : planes) {
            float px = p[0] > 0 ? maxX : minX;
            float py = p[1] > 0 ? maxY : minY;
            float pz = p[2] > 0 ? maxZ : minZ;
            if (p[0]*px + p[1]*py + p[2]*pz + p[3] < 0) return true;
        }
        return false;
    }

    private void normalize(float[] p) {
        float len = (float) Math.sqrt(p[0]*p[0] + p[1]*p[1] + p[2]*p[2]);
        if (len > 0) { p[0]/=len; p[1]/=len; p[2]/=len; p[3]/=len; }
    }
}
