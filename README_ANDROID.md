# Xenia for Android

<div align="center">

![Xenia Logo](assets/icon/256.png)

**Xbox 360 Emulator for Android**

[![Android CI](https://github.com/deivid22srk/xenia-droid/actions/workflows/build.yml/badge.svg)](https://github.com/deivid22srk/xenia-droid/actions/workflows/build.yml)
[![License](https://img.shields.io/badge/license-BSD-blue.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%208.0%2B-green.svg)](https://www.android.com/)

</div>

---

## ⚠️ Project Status

**This is an EXPERIMENTAL port in ALPHA development stage.**

- ✅ UI Framework Complete
- ✅ Build System Ready
- 🚧 Core Emulation In Progress
- ❌ Not Yet Functional

**Current Completion: ~15%** (Foundation & UI)

See [PROGRESS.md](PROGRESS.md) for detailed development tracking.

---

## 📱 About

Xenia for Android is a port of the [Xenia Xbox 360 emulator](https://github.com/xenia-project/xenia) to the Android platform. This project aims to bring Xbox 360 game emulation to modern Android devices with powerful hardware.

### Features (Planned)
- 🎮 Full Xbox 360 game compatibility (target)
- 🎨 Vulkan-based graphics rendering
- 🎵 Audio emulation
- 🎯 Controller support (Bluetooth/USB)
- 💾 Save states
- ⚙️ Customizable settings
- 📱 Modern Material 3 UI

---

## 📋 Requirements

### Minimum Requirements
- **Android Version:** 8.0 (API 26) or higher
- **Architecture:** ARM64-v8a or x86_64
- **RAM:** 4GB minimum, 8GB recommended
- **Storage:** 2GB+ free space
- **GPU:** Vulkan 1.1+ support
- **CPU:** High-performance cores (Snapdragon 855+ / Exynos 9820+ / Kirin 980+)

### Recommended Devices
- **Flagship phones** (2020+): Snapdragon 865+, Exynos 2100+, Tensor G1+
- **Gaming phones**: ASUS ROG Phone, Lenovo Legion, RedMagic
- **Tablets**: High-end Android tablets with active cooling

---

## 🚀 Installation

### From GitHub Releases (Coming Soon)
1. Download the latest APK from [Releases](https://github.com/deivid22srk/xenia-droid/releases)
2. Enable "Install from Unknown Sources" in Android settings
3. Install the APK
4. Grant storage permissions when prompted

### From GitHub Actions (Development Builds)
1. Go to [Actions](https://github.com/deivid22srk/xenia-droid/actions)
2. Select latest successful workflow run
3. Download `xenia-android-debug` artifact
4. Extract and install the APK

---

## 🔨 Building from Source

### Prerequisites
- **Android Studio:** Jellyfish (2023.3.1) or newer
- **JDK:** 17 or higher
- **Android SDK:** API 34
- **Android NDK:** r26b
- **CMake:** 3.22.1+
- **Git:** with submodules support

### Build Steps

```bash
# Clone the repository with submodules
git clone --recursive https://github.com/deivid22srk/xenia-droid.git
cd xenia-droid

# Option 1: Build with Gradle (Command Line)
./gradlew assembleDebug

# Option 2: Build with Android Studio
# Open the project in Android Studio and click "Build > Build Bundle(s) / APK(s) > Build APK(s)"

# Install to connected device
./gradlew installDebug
```

### Build Outputs
- **Debug APK:** `app/build/outputs/apk/debug/app-debug.apk`
- **Release APK:** `app/build/outputs/apk/release/app-release-unsigned.apk`

---

## 📖 Usage (When Functional)

1. **Launch the app**
2. **Tap the (+) button** to select a game
3. **Browse** to your Xbox 360 game file (`.xex`, `.iso`)
4. **Select the game** to start emulation
5. **Configure settings** in the Settings tab as needed

### Supported File Formats
- `.xex` - Xbox 360 executable
- `.iso` - Xbox 360 disc image

---

## ⚙️ Configuration

### Graphics Settings
- **Vulkan Renderer:** High-performance rendering (recommended)
- **VSync:** Synchronize with display refresh rate
- **Resolution Scaling:** Adjust rendering resolution

### Audio Settings
- **Enable Audio:** Toggle audio emulation
- **Audio Latency:** Adjust buffer size

### Input Settings
- **Controller Mapping:** Configure button layout
- **On-screen Controls:** Touch overlay (planned)

---

## 🏗️ Architecture

### Technology Stack
```
┌─────────────────────────────────────┐
│         Kotlin UI Layer             │
│   (Jetpack Compose + Material 3)    │
├─────────────────────────────────────┤
│         JNI Bridge Layer            │
│     (Kotlin ↔ C++ Interface)        │
├─────────────────────────────────────┤
│       Xenia Android Wrapper         │
│    (Lifecycle & Platform Adapter)   │
├─────────────────────────────────────┤
│         Xenia Core Engine           │
│  (CPU, GPU, APU, Kernel, VFS...)    │
└─────────────────────────────────────┘
```

### Key Components
- **UI:** Jetpack Compose with Material 3 design
- **JNI Bridge:** Bidirectional Kotlin-C++ communication
- **Graphics:** Vulkan renderer
- **Audio:** AAudio/OpenSL ES
- **Input:** Android input system integration

---

## 🐛 Known Issues

### Current Limitations
- ⚠️ **Not functional yet** - Core emulation not integrated
- ⚠️ **UI only** - No actual game emulation
- ⚠️ **No file loading** - File picker not implemented
- ⚠️ **No rendering** - Graphics backend not ready

### Potential Future Issues
- Performance may vary significantly by device
- Not all games may be compatible
- Thermal throttling may affect gameplay
- High battery consumption during emulation

---

## 🤝 Contributing

Contributions are welcome! This is a massive project and help is appreciated.

### How to Contribute
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Areas Needing Help
- 🎮 Core emulation integration
- 🎨 Vulkan graphics backend
- 🎵 Audio system implementation
- 🎯 Input system and controller mapping
- 📱 UI/UX improvements
- 🧪 Testing on various devices
- 📖 Documentation

### Code Style
- **Kotlin:** Follow official Kotlin style guide
- **C++:** Follow Xenia project style (C++17)
- Add meaningful comments
- Write descriptive commit messages

---

## 📚 Documentation

- [Progress Tracking](PROGRESS.md) - Detailed development progress
- [Xenia Documentation](docs/) - Original Xenia docs
- [Building Guide](docs/building.md) - Build instructions
- [CPU Documentation](docs/cpu.md) - CPU emulation details
- [GPU Documentation](docs/gpu.md) - GPU emulation details

---

## 🙏 Credits

### Xenia Project
This Android port is based on the amazing work of the Xenia team:
- **GitHub:** https://github.com/xenia-project/xenia
- **Website:** https://xenia.jp/

Special thanks to all Xenia contributors for creating this incredible emulator.

### Android Port
- UI/UX design and implementation
- JNI bridge architecture
- Build system configuration
- Material 3 integration

### Libraries Used
- **Jetpack Compose** - Modern Android UI
- **Material 3** - Design system
- **Kotlin Coroutines** - Asynchronous programming
- **Vulkan** - Graphics API
- **CMake** - Native build system

---

## 📄 License

This project is licensed under the same license as Xenia (BSD 3-Clause).

See [LICENSE](LICENSE) for full details.

---

## ⚖️ Legal

**Xbox 360** is a trademark of Microsoft Corporation.

This project is **not affiliated with, endorsed by, or connected to** Microsoft Corporation or the official Xenia project.

This emulator requires legally obtained game files. **We do not condone piracy.**

---

## 📞 Support

### Getting Help
- 📋 [Open an Issue](https://github.com/deivid22srk/xenia-droid/issues)
- 💬 Check existing issues for solutions
- 📖 Read the documentation

### Reporting Bugs
When reporting bugs, please include:
- Android version and device model
- App version
- Steps to reproduce
- Logcat output if possible
- Game being tested (if applicable)

---

## 🗺️ Roadmap

### Phase 1: Foundation ✅
- ✅ Project structure
- ✅ Build system
- ✅ UI framework
- ✅ JNI bridge

### Phase 2: Core Integration 🚧
- 🚧 CPU emulation
- ⏳ GPU backend (Vulkan)
- ⏳ Audio system
- ⏳ File system

### Phase 3: Features ⏳
- ⏳ Game loading
- ⏳ Controller support
- ⏳ Save states
- ⏳ Settings

### Phase 4: Optimization ⏳
- ⏳ Performance tuning
- ⏳ Battery optimization
- ⏳ Thermal management
- ⏳ Compatibility

### Phase 5: Polish ⏳
- ⏳ UI refinements
- ⏳ Advanced features
- ⏳ Documentation
- ⏳ Release preparation

---

## 📊 Development Statistics

**Lines of Code (Current):**
- Kotlin: ~800 lines
- C++: ~400 lines
- XML/Resources: ~200 lines
- Gradle/Build: ~200 lines

**Files Created:** 30+

**Estimated Time to Functional:** 3-6 months

**Estimated Time to Stable:** 6-12 months

---

<div align="center">

**Made with ❤️ for the Android and emulation communities**

⭐ Star this project if you're interested in following development!

[Report Bug](https://github.com/deivid22srk/xenia-droid/issues) · [Request Feature](https://github.com/deivid22srk/xenia-droid/issues) · [Documentation](PROGRESS.md)

</div>
