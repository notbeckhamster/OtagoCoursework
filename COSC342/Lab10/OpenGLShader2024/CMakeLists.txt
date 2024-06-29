# CMake entry point
cmake_minimum_required (VERSION 3.1)
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(CMAKE_TOOLCHAIN_FILE "/opt/vcpkg/scripts/buildsystems/vcpkg.cmake")


project (COSC_Lab_OpenGLShaders)


find_package(OpenGL REQUIRED)
find_package(glfw3 3.3 REQUIRED)
find_package(glm REQUIRED)


# Compile external dependencies 
add_subdirectory (external) 


if(INCLUDE_DISTRIB)
	add_subdirectory(distrib)
endif(INCLUDE_DISTRIB)



include_directories(
	${GLM_INCLUDE_DIRS}
	${GLFW_INCLUDE_DIRS}
	"external"
	external/assimp-3.0.1270/include/
	.
)

set(ALL_LIBS
	${OPENGL_LIBRARY}
	glfw
)

add_definitions(
	-DTW_STATIC
	-DTW_NO_LIB_PRAGMA
	-DTW_NO_DIRECT3D
	-D_CRT_SECURE_NO_WARNINGS
	-D GLM_ENABLE_EXPERIMENTAL #we use the experimental features of the glm library
)

# OpenGL Shader Lab 1
add_executable(Part01
	Part01/redTriangle.cpp
	common/Shader.cpp
	common/Shader.hpp
	common/Camera.hpp
	common/Camera.cpp
	common/Scene.hpp
	common/Scene.cpp
	common/Object.hpp
	common/Object.cpp
	common/Triangle.hpp
	common/Triangle.cpp
	common/Quad.hpp
	common/Quad.cpp
	Part01/basicShader.frag
	Part01/basicShader.vert
	Part01/colorShader.frag
	Part01/colorShader.vert
	Part01/ColorShader.cpp
	Part01/ColorShader.hpp
)
target_link_libraries(Part01
	${ALL_LIBS}
)
# Xcode and Visual working directories
set_target_properties(Part01 PROPERTIES XCODE_ATTRIBUTE_CONFIGURATION_BUILD_DIR "${CMAKE_CURRENT_SOURCE_DIR}/Part01/")
#create_target_launcher(Part01 WORKING_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/Part01/")



# OpenGL Shaders Part 2
add_executable(Part02
	Part02/modelLoading.cpp
	common/Shader.cpp
	common/Shader.hpp
	common/Camera.hpp
	common/Camera.cpp
	common/Scene.hpp
	common/Scene.cpp
	common/Object.hpp
	common/Object.cpp
	common/Mesh.hpp
	common/Mesh.cpp
	common/SimpleObjloader.hpp
	common/SimpleObjloader.cpp
	common/Controls.hpp
	common/Controls.cpp
	common/Texture.hpp
	common/Texture.cpp
	common/TextureShader.hpp
	common/TextureShader.cpp

	Part02/textureShader.frag
	Part02/textureShader.vert
)
target_link_libraries(Part02
${ALL_LIBS}
assimp
)


set_target_properties(Part02 PROPERTIES COMPILE_DEFINITIONS "USE_ASSIMP")
set_target_properties(Part02 PROPERTIES XCODE_ATTRIBUTE_CONFIGURATION_BUILD_DIR "${CMAKE_CURRENT_SOURCE_DIR}/Part02/")
#create_target_launcher(Part02 WORKING_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/Part02/")


SOURCE_GROUP(common REGULAR_EXPRESSION ".*/common/.*" )
SOURCE_GROUP(shaders REGULAR_EXPRESSION ".*/.*shader$" )

