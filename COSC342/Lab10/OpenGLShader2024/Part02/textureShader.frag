#version 330 core

// Interpolated values from the vertex shaders
in vec2 UV;

// Ouput data
out vec3 color;

// Values that stay constant for the whole mesh.
uniform sampler2D myTextureSampler;

void main()
{
    if (gl_FragCoord.x < 512)
    {
    // Output color = color of the texture at the specified UV
    color = texture( myTextureSampler, UV ).rgb;
   // color = vec3(UV.x, UV.y, 0);
    }
    else
    {
        color = vec3(0, 1, 0);
    }
    
    
}
