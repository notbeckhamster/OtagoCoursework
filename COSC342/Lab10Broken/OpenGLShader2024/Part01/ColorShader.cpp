#include "ColorShader.hpp"

ColorShader::ColorShader(glm::vec4 col, std::string fragShaderName) : Shader(fragShaderName)
{
	color = col;
	colorID = glGetUniformLocation(programID, "colorValue");
	glProgramUniform4fv(programID, colorID, 1, &color[0]);
}

void ColorShader::setColor(glm::vec4 newcolor)
{
	color = newcolor;
	glProgramUniform4fv(programID, colorID, 1, &color[0]);
}


ColorShader::~ColorShader(){
	
	glDeleteProgram(programID);
	
}