#version 330 core

// Ouput data
out vec3 color;
uniform vec4 colorValue;
void main()
{
	color = colorValue.rgb;

}
