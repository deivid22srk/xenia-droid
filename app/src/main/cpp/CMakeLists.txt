cmake_minimum_required(VERSION 3.22.1)

project("xenia-android" CXX C)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_C_STANDARD 11)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fPIC")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fPIC -fexceptions -frtti -Wno-invalid-offsetof -Wno-unused-parameter")
set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -O3 -DNDEBUG")
set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -O0 -g")

add_compile_definitions(
    ANDROID
    __ANDROID__
    XE_PLATFORM_ANDROID=1
    XE_PLATFORM_LINUX=1
    XE_ARCH_ARM64=1
    XE_COMPILER_CLANG=1
)

include_directories(
    ${CMAKE_CURRENT_SOURCE_DIR}
    ${CMAKE_CURRENT_SOURCE_DIR}/xenia
    ${CMAKE_CURRENT_SOURCE_DIR}/third_party
    ${CMAKE_CURRENT_SOURCE_DIR}/third_party/fmt/include
)

file(GLOB ANDROID_WRAPPER_SOURCES
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia_android.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia_jni.cpp"
)

file(GLOB XENIA_BASE_SOURCES
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/arena.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/bit_map.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/bit_stream.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/byte_stream.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/clock.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/clock_posix.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/console_posix.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/console_app_main_android.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/cvar.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/cvar_android.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/debugging_posix.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/exception_handler.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/exception_handler_posix.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/filesystem.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/filesystem_posix.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/filesystem_android.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/filesystem_wildcard.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/fuzzy.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/host_thread_context.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/logging.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/main_android.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/main_init_android.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/mapped_memory_posix.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/memory.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/memory_posix.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/mutex.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/profiling.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/ring_buffer.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/string.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/string_buffer.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/system_android.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/threading.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/threading_posix.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/threading_timer_queue.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/utf8.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/vec128.cc"
)

file(GLOB XENIA_CORE_SOURCES
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/memory.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/xenia/config.cc"
)

file(GLOB THIRD_PARTY_FMT_SOURCES
    "${CMAKE_CURRENT_SOURCE_DIR}/third_party/fmt/src/format.cc"
    "${CMAKE_CURRENT_SOURCE_DIR}/third_party/fmt/src/os.cc"
)

file(GLOB THIRD_PARTY_XXHASH_SOURCES
    "${CMAKE_CURRENT_SOURCE_DIR}/third_party/xxhash/xxhash.c"
)

add_library(${CMAKE_PROJECT_NAME} SHARED
    ${ANDROID_WRAPPER_SOURCES}
    ${XENIA_BASE_SOURCES}
    ${XENIA_CORE_SOURCES}
    ${THIRD_PARTY_FMT_SOURCES}
    ${THIRD_PARTY_XXHASH_SOURCES}
)

target_compile_definitions(${CMAKE_PROJECT_NAME} PRIVATE
    XXH_INLINE_ALL
    FMT_HEADER_ONLY=0
)

find_library(log-lib log)
find_library(android-lib android)

target_link_libraries(${CMAKE_PROJECT_NAME}
    ${log-lib}
    ${android-lib}
)
