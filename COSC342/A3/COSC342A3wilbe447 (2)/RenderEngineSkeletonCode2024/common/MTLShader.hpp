/*
 * MTLShader.hpp
 *
 * Shader for mtl file supporting diffuse, ambient, specular, opacity
 * by Stefanie Zollmann
 *
 *
 */
#ifndef MTLSHADER_HPP
#define MTLSHADER_HPP

#include "Shader.hpp"
class Texture;

class MTLShader: public Shader{
    public:
        //!Default constructor
        /*! Setup default shader. */
        MTLShader();
        //! Constructor
        /*! Version of constructor that allows for  vertex and fragment shader with differnt names */
        MTLShader(std::string vertexshaderName, std::string fragmentshaderName);
        //! Constructor
        /*! Version of constructor that assumes that vertex and fragment shader have same names */
        MTLShader(std::string shaderName);
        //! Destructor
        /*! Clean up ressources */
        ~MTLShader();
        //! setUpShaderParameters
        /*! Set up all shader parameters */
        void setUpShaderParameters();
        //! setUpSsetTexturehaderParameters
        /*! Set up all shader parameters */
        void setTexture(Texture* texture);
        //! setLightPos
        /*! Set the position of the light source. */
        void setLightPos(glm::vec3 lightPos);
        //! setDiffuse
        /*! Set the diffuse color */
        void setDiffuse(glm::vec3 diffuse);
        //! setAmbient
        /*! Set the ambient color */
        void setAmbient(glm::vec3 ambient);
        //! setSpecular
        /*! Set the specular color */
        void setSpecular(glm::vec3 specular);
        //! setOpacity
        /*! Set amount of opacity */
        void setOpacity(float opacity);
        //! setSpecularExponent
        /*! Set amount of shininess (specular exponent) */
        void setSpecularExponent(float specularExponent);
        //! bind
        /*! Bind the shader */
        void bind();


    private:
        glm::vec4 m_diffuseColor;   //!< diffuse color of the material
        glm::vec4 m_ambientColor;   //!<  ambient color of the material
        glm::vec4 m_specularColor;  //!<  specular color of the material
        Texture* m_texture;     //!< texture
        GLuint m_TextureID;     //!< texture id
        GLuint m_lightPosID;    //!< id of the position of the light source
        glm::vec3 m_lightPos;   //!< position of the light source
        float m_opacity;        //!< opacity of the material
        float m_specularExponent; //!< specularExponent of the material


};


#endif
