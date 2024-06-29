CMAKE_MINIMUM_REQUIRED(VERSION "3.12")

set(CMAKE_CXX_STANDARD 14)

project(Stereo)

set(OPENCV_DIR "/usr/include/opencv4")
find_package(OpenCV CONFIG REQUIRED)
include_directories (${OpenCV_INCLUDE_DIRS})
#link_directories( ${OpenCV_LIB_DIR} )

add_executable(stereo stereo.cpp)
target_link_libraries(stereo ${OpenCV_LIBS})
add_executable(stereoFiltering stereoFiltering.cpp)
target_link_libraries(stereoFiltering ${OpenCV_LIBS})
