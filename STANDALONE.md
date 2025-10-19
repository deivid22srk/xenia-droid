# Xenia Android - Standalone Repository Structure

## 📁 Project Structure (Self-Contained)

This Android project is now **fully self-contained** with all necessary Xenia C++ code included directly in the app.

```
app/src/main/
├── cpp/                              # Native C++ code
│   ├── xenia_android.cpp            # Android wrapper
│   ├── xenia_android.h
│   ├── xenia_jni.cpp                # JNI bindings
│   ├── CMakeLists.txt               # Build configuration
│   │
│   ├── xenia/                       # Xenia core code
│   │   ├── base/                    # Base layer (36+ files)
│   │   │   ├── main_android.cc     # Android initialization
│   │   │   ├── memory_posix.cc     # Memory management
│   │   │   ├── threading_posix.cc  # Threading
│   │   │   ├── filesystem_android.cc
│   │   │   ├── logging.cc
│   │   │   └── ... (30+ more files)
│   │   ├── memory.cc/h              # Memory system
│   │   ├── config.cc/h              # Configuration
│   │   └── xbox.h                   # Xbox types
│   │
│   └── third_party/                 # Third-party libraries
│       ├── fmt/                     # String formatting
│       │   ├── format.h
│       │   └── src/format.cc
│       └── xxhash/                  # Fast hashing
│           ├── xxhash.h
│           └── xxhash.c
│
├── java/com/xenia/emulator/         # Kotlin UI code
│   ├── MainActivity.kt
│   ├── ui/
│   │   ├── XeniaApp.kt
│   │   ├── screens/
│   │   └── theme/
│   └── native/
│       └── XeniaNative.kt           # JNI interface
│
└── res/                             # Android resources
    ├── values/
    └── mipmap-*/
```

## ✅ All Files Included

No external dependencies on the Xenia repository! Everything needed is here:

**Xenia Core:**
- ✅ 36+ base layer source files (.cc)
- ✅ 50+ base layer headers (.h)
- ✅ Memory system
- ✅ Configuration system

**Third-Party:**
- ✅ fmt library (complete)
- ✅ xxhash library (complete)

**Android:**
- ✅ Kotlin UI (10 files)
- ✅ JNI bridge
- ✅ Native wrapper

## 🔧 Build System

CMake now uses **local paths only**:
```cmake
${CMAKE_CURRENT_SOURCE_DIR}/xenia/base/*.cc
${CMAKE_CURRENT_SOURCE_DIR}/third_party/fmt/src/*.cc
```

No references to external repositories!

## 🚀 Ready for Standalone Repository

This project can now be:
1. Cloned independently
2. Built without Xenia repository
3. Distributed as separate Android project
4. Maintained in its own repo

## 📱 Orientation Fixed

App now opens in **portrait mode** (vertical) by default.
Removed `android:screenOrientation="sensorLandscape"` from manifest.

## 📊 File Count

**Total C++ files in app:** 90+
- Xenia base: 36 .cc files
- Xenia headers: 50+ .h files
- Third-party: 4 files
- Wrapper: 3 files

**Self-contained:** YES ✅
**External dependencies:** NONE ✅

---

**Last Updated:** October 19, 2025  
**Status:** Standalone Android project ready for independent repository
