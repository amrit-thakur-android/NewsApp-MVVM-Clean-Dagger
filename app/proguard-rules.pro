# NewsApp ProGuard Rules for Play Store Release

# ================================
# GENERAL ANDROID RULES
# ================================

# Keep line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items)
-keepattributes Signature, InnerClasses, EnclosingMethod

# Keep generic signature of suspend functions (CoroutineContext)
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# ================================
# RETROFIT RULES
# ================================

# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep inherited services.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface * extends <1>

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# ================================
# OKHTTP RULES
# ================================

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-adaptresourcefilenames okhttp3/internal/publicsuffix/PublicSuffixDatabase.gz

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt and other security providers are available.
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# ================================
# MOSHI RULES
# ================================

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.squareup.moshi.JsonAdapter
-keep,allowobfuscation,allowshrinking class * extends com.squareup.moshi.JsonAdapter

# To use Methods and Fields introspection capabilities, both need to be allowed.
-keepclassmembers,allowobfuscation class * {
  @com.squareup.moshi.Json <fields>;
}

# Keep ToJson/FromJson-annotated methods
-keepclassmembers class * {
  @com.squareup.moshi.FromJson <methods>;
  @com.squareup.moshi.ToJson <methods>;
}

# Keep @JsonClass generated adapters
-keep class **JsonAdapter {
    <init>(...);
    <fields>;
}

# Keep classes annotated with @JsonClass
-keep @com.squareup.moshi.JsonClass class * { *; }

# Keep the generated JsonAdapter classes
-keep class * extends com.squareup.moshi.JsonAdapter

# ================================
# DAGGER RULES
# ================================

# Keep Dagger generated classes
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class **_MembersInjector { *; }
-keep class **_Factory { *; }
-keep class **_Provide*Factory { *; }

# Keep classes with @Component, @Module, @Provides, @Inject annotations
-keep @dagger.Component class * { *; }
-keep @dagger.Module class * { *; }
-keep @dagger.Subcomponent class * { *; }

-keepclassmembers class * {
    @dagger.Provides <methods>;
    @javax.inject.Inject <methods>;
    @javax.inject.Inject <fields>;
    @javax.inject.Inject <init>(...);
}

# Keep Dagger generated component implementations
-keep class **_Impl { *; }
-keep class **DaggerApplicationComponent { *; }

# ================================
# YOUR DATA CLASSES & API MODELS
# ================================

# Keep all DTO classes (they use @JsonClass annotation)
-keep class com.amritthakur.newsapp.data.remote.dto.** { *; }
-keep class com.amritthakur.newsapp.data.remote.response.** { *; }

# Keep domain entities
-keep class com.amritthakur.newsapp.domain.entity.** { *; }

# Keep API service interfaces
-keep interface com.amritthakur.newsapp.data.remote.api.** { *; }

# ================================
# ANDROIDX & COMPOSE RULES
# ================================

# Keep Compose runtime classes
-keep class androidx.compose.runtime.** { *; }

# Keep Paging library classes
-keep class androidx.paging.** { *; }

# Keep Navigation Compose
-keep class androidx.navigation.** { *; }

# ================================
# COROUTINES RULES
# ================================

# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Same story for the standard library's SafeContinuation that also uses AtomicReferenceFieldUpdater
-keepclassmembers class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}

# ================================
# COIL IMAGE LOADING RULES
# ================================

# Keep Coil classes
-keep class coil3.** { *; }
-dontwarn coil3.**

# ================================
# GENERAL KOTLIN RULES
# ================================

# Keep Kotlin metadata
-keepattributes *Annotation*

# Keep Kotlin intrinsics
-keep class kotlin.Metadata { *; }

# Keep companion objects
-keepclassmembers class * {
    public static ** Companion;
}

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ================================
# DEBUGGING (REMOVE FOR PRODUCTION)
# ================================

# Uncomment to enable more verbose ProGuard output for debugging
# -verbose
# -printmapping mapping.txt
# -printseeds seeds.txt
# -printusage usage.txt