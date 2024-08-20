plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.theweatherapp.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.theweatherapp.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    // retrofit

    implementation(libs.androidx.activity.compose)
    implementation(libs.play.services.location)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.compose.ui.tooling)

    implementation (libs.androidx.ui.v140)
    implementation( libs.androidx.material3.v110)
    implementation( libs.androidx.ui.tooling.preview.v140)
    implementation( libs.androidx.activity.compose.v191)
    debugImplementation (libs.androidx.ui.tooling.v140)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // Lifecycle ViewModel Compose
    implementation( libs.androidx.lifecycle.viewmodel.compose.v261)

    // Kotlin Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    // Location
    implementation (libs.androidx.core.ktx)


}