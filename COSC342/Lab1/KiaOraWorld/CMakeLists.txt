cmake_minimum_required(VERSION 3.24)
project(KiaOraWorld)

set(SOURCE_FILES
  kiaora.cpp
  Image.cpp)
  
add_executable(${PROJECT_NAME}
  ${SOURCE_FILES}
)
