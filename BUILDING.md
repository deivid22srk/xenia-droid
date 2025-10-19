# Xenia Android - Build Instructions

## Current Status: CORE INTEGRATION PHASE

The project is now integrating the real Xenia C++ codebase. This is a complex process and requires careful setup.

---

## Prerequisites

### Required Software
1. **Android Studio** Jellyfish (2023.3.1) or newer
2. **JDK 17** or higher  
3. **Android SDK API 34**
4. **Android NDK r26b** (26.1.10909125)
5. **CMake 3.22.1** or higher
6. **Version control** with submodules

### System Requirements
- **OS:** Linux, macOS, or Windows with WSL2
- **RAM:** 16GB minimum (32GB recommended for builds)
- **Disk Space:** 20GB free space
- **CPU:** Multi-core processor (8+ cores recommended)

---

## Repository Setup

### 1. Clone Repository
The `--recursive` flag is essential as it downloads all Xenia source code and third-party dependencies.

### 2. Verify Submodules
Check if `src/xenia` exists and if `third_party` libraries (fmt, xxhash, etc.) are present.

---

## Build Process

### Method 1: Android Studio (Recommended)

1. **Open Project:** File > Open > Select xenia-droid directory
2. **Wait for Gradle Sync** (10-15 minutes first time)
3. **Select Build Variant:** Build > Select Build Variant > debug or release
4. **Build APK:** Build > Build Bundle(s) / APK(s) > Build APK(s)
5. **Check Output:** `app/build/outputs/apk/debug/app-debug.apk`

### Method 2: Command Line

```bash
cd xenia-droid
chmod +x gradlew
./gradlew clean
./gradlew assembleDebug
```

---

## Current Build Status

### ✅ What Should Compile
- Kotlin UI layer (complete)
- JNI bridge
- Android wrapper (xenia_android.cpp)
- Xenia base layer (30+ files)
- Memory system
- Third-party libraries (fmt, xxhash)

### ⚠️ Expected Issues
- Some Xenia internal headers may need path adjustments
- Platform-specific includes may need CMake guards
- Linker may need additional Android libraries

---

## Testing the Build

### Installation
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Verification via Logcat
```bash
adb logcat -s XeniaAndroid
```

Expected output:
```
I/XeniaAndroid: Initializing Xenia Android...
I/XeniaAndroid: Android API Level: 26
I/XeniaAndroid: Memory system initialized
I/XeniaAndroid: Xenia Android initialized successfully
```

---

## Current Limitations

⚠️ **This is an ALPHA port - not functional yet!**

**What Works:**
- ✅ App launches
- ✅ UI navigation
- ✅ Settings UI
- ✅ Native layer initializes
- ✅ Memory system ready

**What Doesn't Work:**
- ❌ No game loading
- ❌ No emulation
- ❌ No graphics rendering
- ❌ No audio
- ❌ No input

**Expected Timeline:**
- Functional emulation: 2-4 months
- Beta release: 6-8 months

---

**Last Updated:** October 19, 2025  
**Build Status:** 🟡 Partial (Base layer integrated, full build pending)  
**Next Milestone:** CPU core integration
