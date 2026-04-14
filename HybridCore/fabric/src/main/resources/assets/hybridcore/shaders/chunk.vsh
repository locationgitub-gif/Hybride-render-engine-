#version 300 es

// HybridCore — chunk.vsh
// Optimizado para Mali-G52 MC2 (TBDR/Bifrost)
// Nota: usar mediump onde possível reduz pressão no banco de registos

precision mediump float;

in vec3 Position;
in vec2 UV0;
in vec4 Color;
in vec2 UV2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec2 texCoord0;
out vec4 vertexColor;
out vec2 texCoord2;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    texCoord0   = UV0;
    vertexColor = Color;
    texCoord2   = UV2;
}
