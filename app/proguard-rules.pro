# Add project specific ProGuard rules here.

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep Xenia emulator classes
-keep class com.xenia.emulator.** { *; }

# Compose
-dontwarn androidx.compose.**
-keep class androidx.compose.** { *; }
