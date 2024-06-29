# CMake entry point
cmake_minimum_required (VERSION 3.1)
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
#uncomment this line if working from home
set(CMAKE_TOOLCHAIN_FILE "/opt/vcpkg/scripts/buildsystems/vcpkg.cmake")
project (COSC_RenderEngine)

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
	external/assimp-3.0.1270/include/
	external/
	.
)

set(ALL_LIBS
	${OPENGL_LIBRARY}
	glfw
	${GLEW_LIBRARIES}

)

add_definitions(
	-DTW_STATIC
	-DTW_NO_LIB_PRAGMA
	-DTW_NO_DIRECT3D
	-DGLEW_STATIC
	-D_CRT_SECURE_NO_WARNINGS
  -D GLM_ENABLE_EXPERIMENTAL #we use the experimental features of the glm library
)





# OpenGL Skeleton
add_executable(Skeleton
Skeleton/renderApp.cpp
common/Shader.cpp
common/Shader.hpp
common/Texture.cpp
common/Texture.hpp
common/Texture.cpp
common/Texture.hpp
common/Camera.hpp
common/Camera.cpp
common/Scene.hpp
common/Scene.cpp
common/Object.hpp
common/Object.cpp
common/MTLShader.hpp
common/MTLShader.cpp
common/Mesh.hpp
common/Mesh.cpp
common/Material.hpp
common/Material.cpp
common/Group.hpp
common/Group.cpp
common/Objloader.hpp
common/Objloader.cpp
common/Controls.hpp
common/Controls.cpp
common/PostProcessingShader.hpp
common/PostProcessingShader.cpp
common/Quad.hpp
common/Quad.cpp

Skeleton/mtlShader.frag
Skeleton/mtlShader.vert
Skeleton/Passthrough.vert
Skeleton/PostEffectShader.frag
)
target_link_libraries(Skeleton
${ALL_LIBS}
assimp
)

set_target_properties(Skeleton PROPERTIES COMPILE_DEFINITIONS "USE_ASSIMP")
set_target_properties(Skeleton PROPERTIES XCODE_ATTRIBUTE_CONFIGURATION_BUILD_DIR "${CMAKE_CURRENT_SOURCE_DIR}/Skeleton/")

SOURCE_GROUP(common REGULAR_EXPRESSION ".*/common/.*" )
SOURCE_GROUP(shaders REGULAR_EXPRESSION ".*/.*shader$" )
