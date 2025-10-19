cmake_minimum_required(VERSION 3.22.1)

project("xenia-android" CXX C)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fPIC")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fPIC -fexceptions -frtti")

set(XENIA_ROOT "${CMAKE_CURRENT_SOURCE_DIR}/../../../../../src/xenia")

file(GLOB_RECURSE ANDROID_SOURCES
    "${CMAKE_CURRENT_SOURCE_DIR}/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/*.c"
)

add_library(${CMAKE_PROJECT_NAME} SHARED
    ${ANDROID_SOURCES}
)

target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}
    ${XENIA_ROOT}
    ${XENIA_ROOT}/..
)

target_compile_definitions(${CMAKE_PROJECT_NAME} PRIVATE
    ANDROID
    __ANDROID__
    XE_PLATFORM_ANDROID=1
)

find_library(log-lib log)
find_library(android-lib android)
find_library(vulkan-lib vulkan)

target_link_libraries(${CMAKE_PROJECT_NAME}
    ${log-lib}
    ${android-lib}
    ${vulkan-lib}
)
