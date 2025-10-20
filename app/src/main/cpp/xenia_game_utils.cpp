#include "xenia_game_utils.h"
#include <jni.h>
#include <android/log.h>
#include <fstream>
#include <vector>

#include "xenia/kernel/util/xdbf_utils.h"
#include "xenia/cpu/xex_module.h"
#include "xenia/base/filesystem.h"
#include "xenia/base/memory.h"

#define LOG_TAG "XeniaGameUtils"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

namespace xenia {
namespace android {

bool ExtractGameIconFromXex(const std::string& xex_path, const std::string& output_path) {
    LOGI("Extracting icon from XEX: %s", xex_path.c_str());
    
    try {
        std::ifstream file(xex_path, std::ios::binary | std::ios::ate);
        if (!file.is_open()) {
            LOGE("Failed to open XEX file: %s", xex_path.c_str());
            return false;
        }
        
        std::streamsize size = file.tellg();
        file.seekg(0, std::ios::beg);
        
        std::vector<uint8_t> buffer(size);
        if (!file.read(reinterpret_cast<char*>(buffer.data()), size)) {
            LOGE("Failed to read XEX file");
            return false;
        }
        file.close();
        
        const uint8_t* xex_header = buffer.data();
        uint32_t xex_magic = xe::load_and_swap<uint32_t>(xex_header);
        
        if (xex_magic != 0x58455832) {
            LOGE("Invalid XEX magic: 0x%X", xex_magic);
            return false;
        }
        
        uint32_t resource_info_offset = 0;
        const uint8_t* opt_header_ptr = xex_header + 0x18;
        uint32_t header_count = xe::load_and_swap<uint32_t>(xex_header + 0x14);
        
        for (uint32_t i = 0; i < header_count; i++) {
            uint32_t opt_header_id = xe::load_and_swap<uint32_t>(opt_header_ptr);
            uint32_t opt_header_data = xe::load_and_swap<uint32_t>(opt_header_ptr + 4);
            
            if (opt_header_id == 0x000002FF) {
                resource_info_offset = opt_header_data;
                break;
            }
            
            opt_header_ptr += 8;
        }
        
        if (!resource_info_offset) {
            LOGE("No resource info found in XEX");
            return false;
        }
        
        const uint8_t* resource_data = xex_header + resource_info_offset;
        uint32_t resource_size = xe::load_and_swap<uint32_t>(resource_data);
        
        if (resource_size > 0 && resource_size < size) {
            xe::kernel::util::XdbfGameData game_data(resource_data, resource_size);
            
            if (game_data.is_valid()) {
                auto icon_block = game_data.icon();
                
                if (icon_block) {
                    std::ofstream out_file(output_path, std::ios::binary);
                    if (out_file.is_open()) {
                        out_file.write(reinterpret_cast<const char*>(icon_block.buffer), 
                                     icon_block.size);
                        out_file.close();
                        LOGI("Icon extracted successfully to: %s", output_path.c_str());
                        return true;
                    }
                }
            }
        }
        
        LOGE("Failed to extract icon from XDBF data");
        return false;
        
    } catch (const std::exception& e) {
        LOGE("Exception extracting icon: %s", e.what());
        return false;
    }
}

std::string GetGameTitleFromXex(const std::string& xex_path) {
    try {
        std::ifstream file(xex_path, std::ios::binary | std::ios::ate);
        if (!file.is_open()) {
            return "";
        }
        
        std::streamsize size = file.tellg();
        file.seekg(0, std::ios::beg);
        
        std::vector<uint8_t> buffer(size);
        if (!file.read(reinterpret_cast<char*>(buffer.data()), size)) {
            return "";
        }
        file.close();
        
        const uint8_t* xex_header = buffer.data();
        uint32_t xex_magic = xe::load_and_swap<uint32_t>(xex_header);
        
        if (xex_magic != 0x58455832) {
            return "";
        }
        
        uint32_t resource_info_offset = 0;
        const uint8_t* opt_header_ptr = xex_header + 0x18;
        uint32_t header_count = xe::load_and_swap<uint32_t>(xex_header + 0x14);
        
        for (uint32_t i = 0; i < header_count; i++) {
            uint32_t opt_header_id = xe::load_and_swap<uint32_t>(opt_header_ptr);
            uint32_t opt_header_data = xe::load_and_swap<uint32_t>(opt_header_ptr + 4);
            
            if (opt_header_id == 0x000002FF) {
                resource_info_offset = opt_header_data;
                break;
            }
            
            opt_header_ptr += 8;
        }
        
        if (!resource_info_offset) {
            return "";
        }
        
        const uint8_t* resource_data = xex_header + resource_info_offset;
        uint32_t resource_size = xe::load_and_swap<uint32_t>(resource_data);
        
        if (resource_size > 0 && resource_size < size) {
            xe::kernel::util::XdbfGameData game_data(resource_data, resource_size);
            
            if (game_data.is_valid()) {
                return game_data.title();
            }
        }
        
        return "";
        
    } catch (const std::exception& e) {
        LOGE("Exception getting title: %s", e.what());
        return "";
    }
}

}  // namespace android
}  // namespace xenia
