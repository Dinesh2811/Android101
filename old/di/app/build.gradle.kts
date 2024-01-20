plugins {
//    alias(libs.plugins.application)
//    alias(libs.plugins.kotlinAndroid)
//    alias(libs.plugins.ksp)
//    alias(libs.plugins.hiltAndroid)

    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")   // ksp
//    id("kotlin-kapt")   // kapt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.dinesh.android"
    compileSdk = rootProject.extra["compileSdk"] as Int
//    compileSdkPreview = "UpsideDownCake"

    signingConfigs {
        create("release"){
            storeFile = file("${rootProject.projectDir}/dinesh28-release-key.jks")
            storePassword = "dinesh28Android"
            keyAlias = "dinesh28-key-alias"
            keyPassword = "dinesh28Android"
        }
        getByName("debug") {
            storeFile = file("${rootProject.projectDir}/dinesh28-release-key.jks")
            storePassword = "dinesh28Android"
            keyAlias = "dinesh28-key-alias"
            keyPassword = "dinesh28Android"
        }
    }

    defaultConfig {
        applicationId = "com.dinesh.android"
        minSdk = 26
        targetSdk = rootProject.extra["targetSdk"] as Int
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = rootProject.extra["isReleaseMinifyEnabled"] as Boolean
            isShrinkResources = rootProject.extra["isReleaseShrinkResources"] as Boolean
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            testCoverage {
                enableUnitTestCoverage = rootProject.extra["isReleaseTestCoverage"] as Boolean
                enableAndroidTestCoverage = rootProject.extra["isReleaseTestCoverage"] as Boolean
            }
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
//            applicationIdSuffix = ".debug"
            isMinifyEnabled = rootProject.extra["isDebugMinifyEnabled"] as Boolean
            isShrinkResources = rootProject.extra["isDebugShrinkResources"] as Boolean
            testCoverage {
                enableUnitTestCoverage = rootProject.extra["isDebugTestCoverage"] as Boolean
                enableAndroidTestCoverage = rootProject.extra["isDebugTestCoverage"] as Boolean
            }
        }
    }

//    flavorDimensions.add("versions")
//    productFlavors {
//        create("freeVersion") {     //  if (BuildConfig.FLAVOR == "freeVersion")
//            dimension = "versions"
//            applicationIdSuffix = ".free"
////            applicationId = "com.dinesh.free"
////            versionNameSuffix = "-free"
//        }
//        create("paidVersion") {
//            dimension = "versions"
//            applicationIdSuffix = ".paid"
////            applicationId = "com.dinesh.paid"
////            versionNameSuffix = "-paid"
//        }
//    }

//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_17.toString()
//    }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    lint {
        //  https://developer.android.com/studio/write/lint#snapshot
        baseline = file("lint-baseline.xml")

        disable += "TypographyFractions" + "TypographyQuotes"
        enable += "RtlHardcoded" + "RtlCompat" + "RtlEnabled"
        checkOnly += "NewApi" + "InlinedApi"
        // If set to true, turns off analysis progress reporting by lint.
        quiet = true
        // If set to true (default), stops the build if errors are found.
        abortOnError = false
        // If set to true, lint only reports errors.
        ignoreWarnings = true
        // If set to true, lint also checks all dependencies as part of its analysis.
        // Recommended for projects consisting of an app with library dependencies.
        checkDependencies = true
    }

}

dependencies {
    implementation(project(mapOf("path" to ":old:di:dagger")))
    implementation(project(mapOf("path" to ":old:di:hilt")))

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

    //  Dagger
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
