#version 330 core

// Input vertex data, different for all executions of this shader.
layout(location = 0) in vec3 vertexPosition_modelspace;
layout(location = 1) in vec2 vertexUV;
layout(location = 2) in vec3 vertexNormal_modelspace;

// Output data ; will be interpolated for each fragment.
out vec2 UV;
out vec3 posWorldSpace;
out vec3 normalCameraSpace;
out vec3 eyeDirectionCameraSpace;
out vec3 lightDirectionCameraSpace;

// Values that stay constant for the whole mesh.
uniform mat4 MVP;
uniform mat4 M;
uniform mat4 V;
uniform vec3 lightPosition_worldspace;



void main(){
	
	// Output position of the vertex, in clip space : MVP * position
	gl_Position =  MVP * vec4(vertexPosition_modelspace,1);
	// UV of the vertex. No special space for this one.
	UV = vertexUV;

	// Position of the vertex in world space
	posWorldSpace = (M * vec4(vertexPosition_modelspace,1)).xyz;

	//Vector that goes from the vertex to the camera, in camera space
	// In camera space, the camera is at the origin (0,0,0)
	vec3 vertexPosCameraSpace = (V * M * vec4(vertexPosition_modelspace,1)).xyz;
	eyeDirectionCameraSpace = vec3(0,0,0) - vertexPosCameraSpace;

	//Vector that goes from the vertex to the light in camera space. M is ommited becuase it's identity
	vec3 lightPositionCameraSpace = (V * vec4(lightPosition_worldspace,1)).xyz;
	lightDirectionCameraSpace = lightPositionCameraSpace + eyeDirectionCameraSpace;

	//Normal of the vertex in camera space
	normalCameraSpace = (V * M * vec4(vertexNormal_modelspace, 0)).xyz;
}

