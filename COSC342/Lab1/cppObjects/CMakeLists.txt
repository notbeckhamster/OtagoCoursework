cmake_minimum_required(VERSION 3.1)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

project(cppObjects)
add_executable(cppObjects
  objectsMain.cpp
  Object.h
  Object.cpp
  Derived.h
  Derived.cpp
)