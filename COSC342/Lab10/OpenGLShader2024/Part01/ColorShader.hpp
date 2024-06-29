#pragma once

#include "../common/Shader.hpp"

class ColorShader : public Shader {

public:


	ColorShader(){};
    ~ColorShader();
    ColorShader(glm::vec4 col, std::string fragShaderName);
    void setColor(glm::vec4 newcolor);
    

protected:
    glm::vec4 color;
    GLuint colorID;

};