plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    // Remove iOS targets if you're not using them
    sourceSets {
        commonMain {
            dependencies {

            }
        }

        androidMain {
            dependencies {
                implementation(libs.logging.interceptor) // Add logging interceptor only for Android
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test) // e.g., "org.jetbrains.kotlin:kotlin-test"
            }
        }
    }
}

android {
    namespace = "com.example.theweatherapp"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
