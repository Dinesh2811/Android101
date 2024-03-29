plugins {
//    alias(libs.plugins.application)
//    alias(libs.plugins.kotlinAndroid)
//    alias(libs.plugins.ksp)
//    alias(libs.plugins.hiltAndroid)

    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")   // ksp
//    id("kotlin-kapt")   // kapt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.dinesh.hilt"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = rootProject.extra["isReleaseMinifyEnabled"] as Boolean
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = rootProject.extra["isDebugMinifyEnabled"] as Boolean
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    kotlin {
        jvmToolchain(rootProject.extra["jvmToolchain"] as Int)
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["kotlinCompilerExtensionVersion"] as String
    }
}

dependencies {
    implementation(libs.bundles.android)
    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    //  ViewModel & LiveData
    implementation(libs.bundles.lifecycle)

    //  Room components
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)    //  kapt or ksp

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Gson
    implementation(libs.bundles.gson)

    // HTTP
    implementation(libs.bundles.okhttp)

    // Chucker
    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    // Navigation Component
    implementation(libs.bundles.navigation)

    // Paging
    implementation(libs.bundles.paging)

    // Animation
    implementation(libs.bundles.animation)

    // Location Services
    implementation(libs.play.services.location)

    // RecyclerView
    implementation(libs.androidx.swiperefreshlayout)

    // Volley
    implementation(libs.volley)

    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.debug)
    testImplementation(libs.bundles.test)

//    //  Dagger
//    implementation(libs.bundles.dagger)
//    ksp(libs.dagger.compiler)

    //  Hilt
    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.compiler)

    // WorkManager
    implementation(libs.work.manager)

    implementation("androidx.core:core-splashscreen:1.0.1")     // Splash Screen
    debugImplementation("com.squareup.leakcanary:leakcanary-android:3.0-alpha-1")     // LeakCanary

    // Pluto
    debugImplementation("com.plutolib:pluto:2.2.1")
    debugImplementation("com.plutolib.plugins:exceptions:2.2.1")
    releaseImplementation("com.plutolib.plugins:exceptions-no-op:2.2.1")
    releaseImplementation("com.plutolib:pluto-no-op:2.2.1")
}
