plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.chaesiktak"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.chaesiktak"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    dependencies {
        implementation(libs.androidx.core.ktx) // Core KTX
        implementation(libs.androidx.appcompat) // AppCompat
        implementation(libs.material) // Material Design
        implementation(libs.androidx.activity) // Activity KTX
        implementation(libs.androidx.constraintlayout) // ConstraintLayout
        testImplementation(libs.junit) // JUnit for unit testing
        androidTestImplementation(libs.androidx.junit) // JUnit for Android testing
        androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI testing
    }

}