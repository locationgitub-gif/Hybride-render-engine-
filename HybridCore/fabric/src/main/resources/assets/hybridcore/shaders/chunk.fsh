#version 300 es

// HybridCore — chunk.fsh
// TBDR: evitar discard() — quebra o Deferred Shading do tile Mali
// Usar mediump para reduzir uso de registos

precision mediump float;

in vec2 texCoord0;
in vec4 vertexColor;
in vec2 texCoord2;

uniform sampler2D Sampler0; // atlas de blocos
uniform sampler2D Sampler2; // lightmap

out vec4 fragColor;

void main() {
    vec4 tex   = texture(Sampler0, texCoord0);
    vec4 light = texture(Sampler2, texCoord2);

    // Transparência total → descarta sem escrever depth (cuida TBDR)
    if (tex.a < 0.1) discard;

    fragColor = tex * vertexColor * light;
}
