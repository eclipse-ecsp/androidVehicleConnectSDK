plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("org.jetbrains.dokka")
    id("org.cyclonedx.bom")
}

android {
    namespace = "com.harman.androidvehicleconnectsdk"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        group = "com.harman.androidvehicleconnectsdk"
        version = 1.0
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.21")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.google.code.gson:gson:2.12.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("net.openid:appauth:0.11.1")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.core:core-ktx:1.12.0")

    implementation("com.google.dagger:dagger:2.55")
    implementation("com.google.dagger:dagger-android:2.55")
    implementation("com.google.dagger:dagger-android-support:2.55")
    kapt("com.google.dagger:dagger-android-processor:2.55")
    kapt("com.google.dagger:dagger-compiler:2.55")
}
