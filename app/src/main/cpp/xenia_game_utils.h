#ifndef XENIA_GAME_UTILS_H_
#define XENIA_GAME_UTILS_H_

#include <string>

namespace xenia {
namespace android {

bool ExtractGameIconFromXex(const std::string& xex_path, const std::string& output_path);

std::string GetGameTitleFromXex(const std::string& xex_path);

}  // namespace android
}  // namespace xenia

#endif  // XENIA_GAME_UTILS_H_
