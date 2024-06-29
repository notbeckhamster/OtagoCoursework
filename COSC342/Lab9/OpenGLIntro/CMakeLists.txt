# CMake entry point
cmake_minimum_required (VERSION 3.1)


set(CMAKE_TOOLCHAIN_FILE "/opt/vcpkg/scripts/buildsystems/vcpkg.cmake")

include(CMakePrintHelpers)
project (COSC_Lab_OpenGL)

cmake_print_variables(CMAKE_PREFIX_PATH)
cmake_print_variables(CMAKE_SOURCE_DIR)


find_package(OpenGL REQUIRED)
find_package(glfw3 REQUIRED)
find_package(glm REQUIRED)


include_directories(
	${GLM_INCLUDE_DIRS}
	"external"
	${GLFW_INCLUDE_DIRS}
	.
)
#we use the experimental features of the glm library
add_definitions(-D GLM_ENABLE_EXPERIMENTAL)


set(ALL_LIBS
	${OPENGL_LIBRARY}
	glfw
)

add_definitions(
	-DTW_STATIC
	-DTW_NO_LIB_PRAGMA
	-DTW_NO_DIRECT3D
	-DGLEW_STATIC
	-D_CRT_SECURE_NO_WARNINGS
)

# OpenGL Part 1
add_executable(Part01
	Part01/simpleWindow.cpp
)
target_link_libraries(Part01
	${ALL_LIBS}
)

# OpenGL Lab 2
add_executable(Part02
	Part02/redTriangle.cpp
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
	Part02/basicShader.frag
	Part02/basicShader.vert

)
target_link_libraries(Part02
	${ALL_LIBS}
)


SOURCE_GROUP(common REGULAR_EXPRESSION ".*/common/.*" )
SOURCE_GROUP(shaders REGULAR_EXPRESSION ".*/.*shader$" )
