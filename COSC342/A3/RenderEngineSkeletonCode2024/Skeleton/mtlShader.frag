#version 330 core

// Interpolated values from the vertex shaders
// e.g.
in vec2 UV;
in vec3 posWorldSpace;
in vec3 normalCameraSpace;
in vec3 eyeDirectionCameraSpace;
in vec3 lightDirectionCameraSpace;

// Output data
out vec4 color;

// Texture values stay constant for the whole mesh.
uniform sampler2D myTextureSampler;

// Material properties stay constant for whole material
uniform vec4 diffuseMatColor;
uniform vec4 ambientMatColor;
uniform vec4 specularMatColor;
uniform float opacity;
uniform float ns;

//Light emission properties stay constant for scene
const vec3 ambientLightColor = vec3(0.4,0.4,0.4);
const vec3 diffuseLightColor = vec3(1.0,1.0,1.0);
const vec3 specularLightColor = vec3(1.0,1.0,1.0);

void main(){
	
	// Material properties
    vec3 textureVal = texture( myTextureSampler, UV ).rgb;
    


    // Normal, Light, Eye, Reflection, Halfway vectors
    vec3 N = normalize(normalCameraSpace);
    vec3 L = normalize(lightDirectionCameraSpace);
    vec3 V = normalize(eyeDirectionCameraSpace);
    vec3 R = reflect(-L, N);
    vec3 H = normalize(L+V);

    // Angles 
    float cosTheta = clamp( dot( N,L), 0,1 );
   float cosAlpha = clamp( dot( H,N), 0,1 ); //Blinn-phong reflection model
    //float cosAlpha = clamp( dot( V,R ), 0,1 ); //Phong reflection model  
    


    // Calculate the reflection components
    vec3 diffuseComponent = diffuseLightColor* diffuseMatColor.rgb * textureVal * cosTheta;
    vec3 ambientComponent = ambientLightColor * ambientMatColor.rgb * textureVal; 
    vec3 specularComponent = specularLightColor * specularMatColor.rgb * pow(cosAlpha,ns);

    // Output color for fragment
    color.rgb = 
    // Ambient : simulates indirect lighting
    ambientComponent +
    // Diffuse : "color" of the object
    diffuseComponent +
    // Specular : reflective highlight, like a mirror
    specularComponent;
    
    
    // Transparency
    color.a = opacity;
    
}
