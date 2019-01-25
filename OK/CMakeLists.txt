cmake_minimum_required(VERSION 3.10)
project(OK)

set(CMAKE_CXX_STANDARD 17)

add_executable(OK main.cpp)
add_compile_options(-Wall -Wextra -pedantic -Werror)
