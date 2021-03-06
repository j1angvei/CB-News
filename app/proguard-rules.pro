# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Wayne\AppData\Local\Android\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For moreData details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
##make gson deserilize work on beans
-keepclassmembers class cn.j1angvei.cbnews.bean.*{
private <fields>;
}
-keepclassmembers class cn.j1angvei.cbnews.web.*{
private <fields>;
}
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic source information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for Retrofit 2 ----------
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic source information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
##---------------End: proguard configuration for Retrofit 2 ----------

##---------------Begin: proguard configuration for Glide  ----------
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule
##---------------End: proguard configuration for Glide  ----------

##---------------Begin: proguard configuration for Log  ----------
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
##---------------End: proguard configuration for Log  ----------
##---------------Begin: proguard configuration for okio  ----------
-dontwarn okio.**
##---------------Begin: proguard configuration for okio  ----------

##---------------End: proguard configuration for rxJava  ----------
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
##---------------End: proguard configuration for rxJava  ----------

##---------------Begin: proguard configuration for jsoup  ---------
-keeppackagenames org.jsoup.nodes
#-keep public class org.jsoup.** {
#public *;
#}
##---------------End: proguard configuration for jsoup ----------

##---------------Begin: proguard configuration for picasso ---------
#-dontwarn com.squareup.okhttp.**
##---------------End: proguard configuration for picasso ----------

##---------------End: proguard configuration for Leakcanary ----------
-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }

# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification
##---------------End: proguard configuration for Leakcanary ----------
