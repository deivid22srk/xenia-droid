#include "xenia_android.h"

#include "xenia/base/assert.h"
#include "xenia/base/cvar.h"
#include "xenia/base/logging.h"
#include "xenia/base/main_android.h"
#include "xenia/base/string.h"
#include "xenia/base/system.h"
#include "xenia/base/threading.h"
#include "xenia/memory.h"
#include "third_party/fmt/include/fmt/format.h"

namespace xenia {
namespace android {

XeniaAndroid& XeniaAndroid::GetInstance() {
    static XeniaAndroid instance;
    return instance;
}

void XeniaAndroid::SetAndroidContext(JNIEnv* env, jobject context, int32_t api_level) {
    android_context_.env = env;
    android_context_.context = context;
    android_context_.api_level = api_level;
    LOGI("Android context set: API level %d", api_level);
}

bool XeniaAndroid::Initialize() {
    if (initialized_) {
        LOGW("Xenia already initialized");
        return true;
    }
    
    LOGI("Initializing Xenia Android...");
    LOGI("Android API Level: %d", android_context_.api_level);
    
    if (android_context_.env != nullptr && android_context_.context != nullptr) {
        xe::InitializeAndroidAppFromMainThread(
            android_context_.api_level,
            android_context_.env,
            android_context_.context,
            nullptr
        );
    } else {
        LOGW("Android context not set, initializing without JNI");
        xe::InitializeAndroidAppFromMainThread(26, nullptr, nullptr, nullptr);
    }
    
    try {
        memory_ = std::make_unique<xe::Memory>();
        if (!memory_->Initialize()) {
            LOGE("Failed to initialize memory system");
            return false;
        }
        LOGI("Memory system initialized successfully");
    } catch (const std::exception& e) {
        LOGE("Exception during initialization: %s", e.what());
        return false;
    }
    
    initialized_ = true;
    
    LOGI("Xenia Android initialized successfully");
    return true;
}

void XeniaAndroid::Shutdown() {
    if (!initialized_) {
        return;
    }
    
    LOGI("Shutting down Xenia Android...");
    
    if (emulation_running_) {
        StopEmulation();
    }
    
    if (memory_) {
        memory_->Reset();
        memory_.reset();
        LOGI("Memory system shut down");
    }
    
    xe::ShutdownAndroidAppFromMainThread();
    
    initialized_ = false;
    
    LOGI("Xenia Android shut down");
}

std::string XeniaAndroid::GetVersion() const {
    return fmt::format(
        "Xenia Android 1.0.0-alpha\nBuild: {} {}\nMemory: {}",
        __DATE__, __TIME__,
        memory_ ? "Ready" : "Not initialized"
    );
}

bool XeniaAndroid::LoadGame(const std::string& path) {
    if (!initialized_) {
        LOGE("Cannot load game: Xenia not initialized");
        return false;
    }
    
    LOGI("Loading game: %s", path.c_str());
    
    current_game_path_ = path;
    
    LOGI("Game path stored, ready for emulation");
    return true;
}

bool XeniaAndroid::StartEmulation() {
    if (!initialized_) {
        LOGE("Cannot start emulation: Xenia not initialized");
        return false;
    }
    
    if (emulation_running_) {
        LOGW("Emulation already running");
        return true;
    }
    
    LOGI("Starting emulation...");
    
    emulation_running_ = true;
    
    return true;
}

void XeniaAndroid::PauseEmulation() {
    if (!emulation_running_) {
        return;
    }
    
    LOGI("Pausing emulation");
}

void XeniaAndroid::ResumeEmulation() {
    if (!emulation_running_) {
        return;
    }
    
    LOGI("Resuming emulation");
}

void XeniaAndroid::StopEmulation() {
    if (!emulation_running_) {
        return;
    }
    
    LOGI("Stopping emulation");
    
    emulation_running_ = false;
}

bool XeniaAndroid::IsEmulationRunning() const {
    return emulation_running_;
}

void XeniaAndroid::SetSurfaceSize(int width, int height) {
    LOGI("Surface size: %dx%d", width, height);
    surface_width_ = width;
    surface_height_ = height;
}

void XeniaAndroid::SetRendererType(int type) {
    LOGI("Renderer type: %d", type);
}

void XeniaAndroid::SetVulkanEnabled(bool enabled) {
    LOGI("Vulkan enabled: %d", enabled);
    vulkan_enabled_ = enabled;
}

void XeniaAndroid::SetVSyncEnabled(bool enabled) {
    LOGI("VSync enabled: %d", enabled);
    vsync_enabled_ = enabled;
}

void XeniaAndroid::SetAudioEnabled(bool enabled) {
    LOGI("Audio enabled: %d", enabled);
    audio_enabled_ = enabled;
}

void XeniaAndroid::SendControllerInput(int button, bool pressed) {
    LOGD("Controller input: button=%d, pressed=%d", button, pressed);
}

}
}
