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

-ignorewarnings
-dontskipnonpubliclibraryclassmembers

#-----------------  enable optimization  -----------------#
-optimizations   code/simplification/arithmetic,!code/simplification/cast,!field/*,!method/inlining/*
-optimizationpasses 5
-allowaccessmodification
#-----------------  enable optimization  -----------------#


#-----------------  remove log commands from code   -----------------#
-assumenosideeffects class android.util.Log {
 public static *** d(...);
 public static *** i(...);
 public static *** v(...);
 public static *** e(...);
}

# remove KitLog
-assumenosideeffects class com.hanamin.weather.util.KitLog {
 public static *** e(java.lang.Object);
 public static *** d(java.lang.Object);
 public static *** http(java.lang.Object);

}
#-----------------  remove log commands from code  -----------------#



#-----------------   repackage all classes to a single package. This config would move all classes to a root-level package android/support/v4 -----------------#
#-repackageclasses 'android/support/v4'
#-----------------   repackage all classes to a single package. This config would move all classes to a root-level package android/support/v4 -----------------#


#-----------------  dictioneries  -----------------#
-obfuscationdictionary method-dictionary.txt
-packageobfuscationdictionary package-dictionary.txt
-classobfuscationdictionary class-dictionary.txt
#-----------------  dictioneries  -----------------#


#-----------------  Log File  -----------------#
#-dump class_files.txt
#-printseeds seeds.txt
#-printusage unused.txt
#-printmapping mapping.txt
#-----------------  Log File  -----------------#


#-----------------  attributes  -----------------#
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#-----------------  attributes  -----------------#


#-----------------    -----------------#
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#-----------------  must checked  -----------------#


#-----------------  GLIDE  -----------------#
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#-----------------  GLIDE  -----------------#



-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends androidx.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class androidx** {*;}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}



-dontwarn com.google.**
-keep class com.google.** { *; }


#-keep class com.android.** { *; }
#-keep interface com.android.** { *; }


-dontwarn com.squareup.**
-keep class com.squareup.** { *; }



-dontwarn com.journeyapps.**
-keep class com.journeyapps.** { *; }



-dontwarn com.crashlytics.**
-keep class com.crashlytics.** { *; }


#green robot
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }


#dagger2
-dontwarn com.google.errorprone.annotations.**



# used in tests
-keepclassmembers public class * extends io.reactivex.Observable { public ** blockingFirst(); }


#calligraphy
-keep class uk.co.chrisjenx.calligraphy.* { *; }
-keep class uk.co.chrisjenx.calligraphy.*$* { *; }





#okio
-dontwarn okio.**



# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**

# ------------------- TEST DEPENDENCIES -------------------
-dontwarn org.hamcrest.**
-dontwarn android.test.**
-dontwarn android.support.test.**

-keep class org.hamcrest.** {
   *;
}
-keep class org.junit.** { *; }
-dontwarn org.junit.**

-keep class junit.** { *; }
-dontwarn junit.**

-keep class sun.misc.** { *; }
-dontwarn sun.misc.**


# ------------------- RX JAVA -------------------
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

# ------------------- DATA BINDING -------------------
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
-keep class com.hanamin.weather.app.databinding.** { *; }


# ------------------- RETROFIT -------------------
#-dontwarn okio.**
#-dontwarn retrofit2.Platform$Java8
#-keepclasseswithmembers class * {
#    @retrofit2.http.* <methods>;
#}
#-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }
#
-keepattributes Exceptions

-if interface * { @retrofit2.http.* *** *(...); }
-keep,allowobfuscation interface <3>

# ----------------- Application classes that will be serialized/deserialized over Gson, keepclassmembers -------------------#
-keep class ir.iccard.app.models.local** { *; }
-keepclassmembers class ir.iccard.app.models.local** { *; }
-keep class ir.iccard.app.models.remote** { *; }
-keepclassmembers class ir.iccard.app.models.remote** { *; }

# ------------------- SOCKET.IO -------------------
-keep class io.socket.** {
   *;
}


# ------------------- BARCOD SCANNER -------------------
-keep class com.dlazaro66.** {
   *;
}




# ------------------- indicator animation -------------------
-keepclassmembers class androidx** { public void setRotation(android.view.View, float); }
-keepclassmembers class androidx** { public void setScaleX(android.view.View, float); }
-keepclassmembers class androidx** { public void setScaleY(android.view.View, float); }
-keepclassmembers class androidx** { public void setTranslationX(android.view.View, float); }
-keepclassmembers class androidx** { public void setTranslationY(android.view.View, float); }
-keepclassmembers class androidx** { public void setTranslationZ(android.view.View, float); }
-keepclassmembers class androidx** { public void setAlpha(android.view.View, float); }
-keepclassmembers class androidx** { public void setElevation(android.view.View, float); }
-keepclassmembers class androidx** { public void setPivotX(android.view.View, float); }
-keepclassmembers class androidx** { public void setPivotY(android.view.View, float); }

-keepclassmembers class androidx** { public void setRotation(float); }
-keepclassmembers class androidx** { public void setScaleX(float); }
-keepclassmembers class androidx** { public void setScaleY(float); }
-keepclassmembers class androidx** { public void setTranslationX(float); }
-keepclassmembers class androidx** { public void setTranslationY(float); }
-keepclassmembers class androidx** { public void setTranslationZ(float); }
-keepclassmembers class androidx** { public void setAlpha(float); }
-keepclassmembers class androidx** { public void setElevation(float); }
-keepclassmembers class androidx** { public void setPivotX(float); }
-keepclassmembers class androidx** { public void setPivotY(float); }

-keepclassmembers class androidx** { public float getRotation(float); }
-keepclassmembers class androidx** { public float getScaleX(float); }
-keepclassmembers class androidx** { public float getScaleY(float); }
-keepclassmembers class androidx** { public float getTranslationX(float); }
-keepclassmembers class androidx** { public float getTranslationY(float); }
-keepclassmembers class androidx** { public float getTranslationZ(float); }
-keepclassmembers class androidx** { public float getAlpha(float); }
-keepclassmembers class androidx** { public float getElevation(float); }
-keepclassmembers class androidx** { public float getPivotX(float); }
-keepclassmembers class androidx** { public float getPivotY(float); }

-keep class android.animation.Animator.** {
   *;
}
-keepclassmembers class android.animation.Animator.** {}


#-------------------- green robot event bus---------------------[
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}



#-------------------- firebase ---------------------
-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.apache.**
-dontwarn org.w3c.dom.**

#keep render script for RealtimeBlurView library
-keep class android.support.v8.renderscript.** { *; }
-keep class androidx.renderscript.** { *; }


#-------------coroutines----------------------------
# ServiceLoader support
        -keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Lottie
-dontwarn com.airbnb.lottie.*
-keep class com.airbnb.lottie.* {*;}




