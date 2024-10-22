plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.studio.ananas.testimageshow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.studio.ananas.testimageshow"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Lifecycle for ViewModel
    implementation(libs.androidx.lifecycle.runtime.ktx.v240)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Retrofit for network requests
    implementation(libs.retrofit)

    // Converter for converting JSON responses to Kotlin objects (Gson converter)
    implementation(libs.converter.gson)

    // OkHttp for logging network requests (optional but useful)
    implementation(libs.logging.interceptor)

    // OkHttp for network requests (for file downloading)
    implementation(libs.okhttp)

    // Kotlin coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)



    implementation(libs.ui)
    implementation(libs.androidx.material)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx.v251)
    implementation(libs.coil.compose) // Coil for image loading
    implementation(libs.exoplayer.v2181) // ExoPlayer for video playback


}