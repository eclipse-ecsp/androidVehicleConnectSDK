plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.cyclonedx.bom")
}

android {
    namespace = "org.eclipse.sdkapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.eclipse.sdkapp"
        minSdk = 24
        targetSdk = 35
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
        group = "org.eclipse.sdkapp"
        version = 1.0
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    lint {
        abortOnError = false
    }
}

dependencies {

    val appCoreKtxVersion: String by project
    val appCompatVersion: String by project
    val vehicleConnectSdkVersion: String by project
    val retrofitVersion: String by project
    val appGsonVersion: String by project
    val okHttpLoggingVersion: String by project
    val junitVersion: String by project
    val mockitoCoreVersion: String by project
    val mockitoInLineVersion: String by project
    val powermockModuleVersion: String by project
    val powermockApiVersion: String by project
    val testRulesVersion: String by project
    val testRunnerVersion: String by project
    val robolectricVersion: String by project
    val testJunitVersion: String by project
    val espressoCoreVersion: String by project

    implementation("androidx.core:core-ktx:$appCoreKtxVersion")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("org.eclipse.ecsp:vehicleconnectsdk:$vehicleConnectSdkVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.google.code.gson:gson:$appGsonVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpLoggingVersion")
    implementation(kotlin("reflect"))

    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.mockito:mockito-core:$mockitoCoreVersion")
    testImplementation("org.mockito:mockito-inline:$mockitoInLineVersion")
    testImplementation("org.powermock:powermock-module-junit4:$powermockModuleVersion")
    testImplementation("org.powermock:powermock-api-mockito2:$powermockApiVersion")
    testImplementation("androidx.test:rules:$testRulesVersion")
    testImplementation("androidx.test:runner:$testRunnerVersion")
    testImplementation("org.robolectric:robolectric:$robolectricVersion")
    androidTestImplementation("androidx.test.ext:junit:$testJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCoreVersion")
}