# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
#-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation







## Add this line to keep all the classes in your project
#-keep class com.dinesh.android.** { *; }
#
## Add these lines for Dagger-Hilt
#-keep class dagger.hilt.android.** { *; }
#-keep class dagger.** { *; }
#-keep class javax.inject.** { *; }
#-keep class dagger.hilt.internal.** { *; }
#-keep class androidx.hilt.** { *; }
#-keep class androidx.hilt.lifecycle.** { *; }
#-keep class androidx.hilt.navigation.** { *; }
#-keep class androidx.lifecycle.ViewModel { *; }
#-keep class androidx.lifecycle.ViewModelProvider { *; }
#
## Add this line to keep all the classes that extend ViewModel
#-keep class * extends androidx.lifecycle.ViewModel
#
## Add this line to keep all the classes that are annotated with @AndroidEntryPoint, @HiltAndroidApp, @HiltAndroidTest, @InstallIn
#-keep @dagger.hilt.android.AndroidEntryPoint class *
#-keep @dagger.hilt.android.HiltAndroidApp class *
#-keep @dagger.hilt.android.ViewModelLifecycle class *

