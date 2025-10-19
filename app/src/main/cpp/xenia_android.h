#ifndef XENIA_ANDROID_H_
#define XENIA_ANDROID_H_

#include <jni.h>
#include <android/log.h>
#include <string>

#define LOG_TAG "XeniaAndroid"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

namespace xenia {
namespace android {

class XeniaAndroid {
public:
    static XeniaAndroid& GetInstance();
    
    bool Initialize();
    void Shutdown();
    
    std::string GetVersion() const;
    
    bool LoadGame(const std::string& path);
    bool StartEmulation();
    void PauseEmulation();
    void ResumeEmulation();
    void StopEmulation();
    bool IsEmulationRunning() const;
    
    void SetSurfaceSize(int width, int height);
    void SetRendererType(int type);
    void SetVulkanEnabled(bool enabled);
    void SetVSyncEnabled(bool enabled);
    void SetAudioEnabled(bool enabled);
    
    void SendControllerInput(int button, bool pressed);

private:
    XeniaAndroid() = default;
    ~XeniaAndroid() = default;
    
    XeniaAndroid(const XeniaAndroid&) = delete;
    XeniaAndroid& operator=(const XeniaAndroid&) = delete;
    
    bool initialized_ = false;
    bool emulation_running_ = false;
    
    int surface_width_ = 0;
    int surface_height_ = 0;
    
    bool vulkan_enabled_ = true;
    bool vsync_enabled_ = true;
    bool audio_enabled_ = true;
};

}
}

#endif
