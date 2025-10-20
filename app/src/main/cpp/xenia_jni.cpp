#include <jni.h>
#include "xenia_android.h"
#include "xenia_game_utils.h"

using namespace xenia::android;

extern "C" {

JNIEXPORT jboolean JNICALL
Java_com_xenia_emulator_native_XeniaNative_initialize(JNIEnv* env, jobject thiz) {
    jclass context_class = env->FindClass("android/content/Context");
    if (context_class) {
        jmethodID get_app_context = env->GetMethodID(context_class, "getApplicationContext", "()Landroid/content/Context;");
        if (get_app_context) {
            jobject app_context = env->CallObjectMethod(thiz, get_app_context);
            if (app_context) {
                jobject global_context = env->NewGlobalRef(app_context);
                XeniaAndroid::GetInstance().SetAndroidContext(env, global_context, 26);
                env->DeleteLocalRef(app_context);
            }
        }
        env->DeleteLocalRef(context_class);
    }
    
    return XeniaAndroid::GetInstance().Initialize() ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNICALL
Java_com_xenia_emulator_native_XeniaNative_shutdown(JNIEnv* env, jobject thiz) {
    XeniaAndroid::GetInstance().Shutdown();
}

JNIEXPORT jstring JNICALL
Java_com_xenia_emulator_native_XeniaNative_getVersion(JNIEnv* env, jobject thiz) {
    std::string version = XeniaAndroid::GetInstance().GetVersion();
    return env->NewStringUTF(version.c_str());
}

JNIEXPORT jboolean JNICALL
Java_com_xenia_emulator_native_XeniaNative_loadGame(JNIEnv* env, jobject thiz, jstring path) {
    const char* path_str = env->GetStringUTFChars(path, nullptr);
    bool result = XeniaAndroid::GetInstance().LoadGame(path_str);
    env->ReleaseStringUTFChars(path, path_str);
    return result ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jboolean JNICALL
Java_com_xenia_emulator_native_XeniaNative_startEmulation(JNIEnv* env, jobject thiz) {
    return XeniaAndroid::GetInstance().StartEmulation() ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNICALL
Java_com_xenia_emulator_native_XeniaNative_pauseEmulation(JNIEnv* env, jobject thiz) {
    XeniaAndroid::GetInstance().PauseEmulation();
}

JNIEXPORT void JNICALL
Java_com_xenia_emulator_native_XeniaNative_resumeEmulation(JNIEnv* env, jobject thiz) {
    XeniaAndroid::GetInstance().ResumeEmulation();
}

JNIEXPORT void JNICALL
Java_com_xenia_emulator_native_XeniaNative_stopEmulation(JNIEnv* env, jobject thiz) {
    XeniaAndroid::GetInstance().StopEmulation();
}

JNIEXPORT jboolean JNICALL
Java_com_xenia_emulator_native_XeniaNative_isEmulationRunning(JNIEnv* env, jobject thiz) {
    return XeniaAndroid::GetInstance().IsEmulationRunning() ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNICALL
Java_com_xenia_emulator_native_XeniaNative_setSurfaceSize(JNIEnv* env, jobject thiz, jint width, jint height) {
    XeniaAndroid::GetInstance().SetSurfaceSize(width, height);
}

JNIEXPORT void JNICALL
Java_com_xenia_emulator_native_XeniaNative_setRendererType(JNIEnv* env, jobject thiz, jint type) {
    XeniaAndroid::GetInstance().SetRendererType(type);
}

JNIEXPORT void JNICALL
Java_com_xenia_emulator_native_XeniaNative_setVulkanEnabled(JNIEnv* env, jobject thiz, jboolean enabled) {
    XeniaAndroid::GetInstance().SetVulkanEnabled(enabled == JNI_TRUE);
}

JNIEXPORT void JNICALL
Java_com_xenia_emulator_native_XeniaNative_setVSyncEnabled(JNIEnv* env, jobject thiz, jboolean enabled) {
    XeniaAndroid::GetInstance().SetVSyncEnabled(enabled == JNI_TRUE);
}

JNIEXPORT void JNICALL
Java_com_xenia_emulator_native_XeniaNative_setAudioEnabled(JNIEnv* env, jobject thiz, jboolean enabled) {
    XeniaAndroid::GetInstance().SetAudioEnabled(enabled == JNI_TRUE);
}

JNIEXPORT void JNICALL
Java_com_xenia_emulator_native_XeniaNative_sendControllerInput(JNIEnv* env, jobject thiz, jint button, jboolean pressed) {
    XeniaAndroid::GetInstance().SendControllerInput(button, pressed == JNI_TRUE);
}

JNIEXPORT jboolean JNICALL
Java_com_xenia_emulator_native_XeniaNative_extractGameIcon(JNIEnv* env, jobject thiz, jstring game_path, jstring output_path) {
    const char* game_path_str = env->GetStringUTFChars(game_path, nullptr);
    const char* output_path_str = env->GetStringUTFChars(output_path, nullptr);
    
    bool result = ExtractGameIconFromXex(game_path_str, output_path_str);
    
    env->ReleaseStringUTFChars(game_path, game_path_str);
    env->ReleaseStringUTFChars(output_path, output_path_str);
    
    return result ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jstring JNICALL
Java_com_xenia_emulator_native_XeniaNative_getGameTitle(JNIEnv* env, jobject thiz, jstring game_path) {
    const char* game_path_str = env->GetStringUTFChars(game_path, nullptr);
    
    std::string title = GetGameTitleFromXex(game_path_str);
    
    env->ReleaseStringUTFChars(game_path, game_path_str);
    
    if (title.empty()) {
        return nullptr;
    }
    
    return env->NewStringUTF(title.c_str());
}

}
