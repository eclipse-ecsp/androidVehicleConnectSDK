plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("org.jetbrains.dokka")
    id("org.cyclonedx.bom")
    id("org.jlleitschuh.gradle.ktlint")
    id("signing")
    id("maven-publish")
}

android {
    namespace = "org.eclipse.ecsp"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        group = "org.eclipse.ecsp"
        version = 1.0
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    ktlint {
        android = true
        ignoreFailures = false
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "org.eclipse.ecsp"
            artifactId = "vehicleconnectsdk"
            version = "1.0.0"
            afterEvaluate {
                from(components["release"])
            }
        }
    }

    repositories {
        maven {
            name = "ossrh"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
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
    implementation("androidx.concurrent:concurrent-futures:1.1.0")
    implementation("androidx.lifecycle:lifecycle-common:2.7.0")

    implementation("com.google.dagger:dagger:2.55")
    implementation("com.google.dagger:dagger-android:2.55")
    implementation("com.google.dagger:dagger-android-support:2.55")
    kapt("com.google.dagger:dagger-android-processor:2.55")
    kapt("com.google.dagger:dagger-compiler:2.55")
}
