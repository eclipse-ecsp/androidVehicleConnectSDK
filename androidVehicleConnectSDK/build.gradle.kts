/********************************************************************************
 * Copyright (c) 2023-24 Harman International
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ********************************************************************************/
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
    id("org.jetbrains.kotlinx.kover")
    id("org.jreleaser") version "1.17.0"
    id("java-base")
}

android {
    namespace = "org.eclipse.ecsp"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["appAuthRedirectScheme"] = "ignite"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
        version = 1.6
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
        filter {
            exclude("**/build/**")
        }
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}
val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from("build/dokka/html")
}

publishing {
    publications {
        register<MavenPublication>("Releases") {
            groupId = "org.eclipse.ecsp"
            artifactId = "vehicleconnectsdk"
            version = "1.1.8"
            artifact(javadocJar)
            artifact(file("build/reports/bom.xml")) {
                classifier = "cyclonedx"
                extension = "xml"
            }
            afterEvaluate {
                from(components["release"])
            }
            pom {
                groupId = "org.eclipse.ecsp"
                artifactId = "vehicleconnectsdk"
                version = "1.1.8"
                name = "$groupId:$artifactId"
                description.set("Android Library with vehicle related APIs, contains set of Login and Remote operation API")
                url.set("https://github.com/eclipse-ecsp/androidVehicleConnectSDK.git")
                packaging = "aar"
                scm {
                    connection.set("scm:git:https://github.com/eclipse-ecsp/androidVehicleConnectSDK.git")
                    developerConnection.set("scm:git:https://github.com/eclipse-ecsp/androidVehicleConnectSDK.git")
                    url.set("https://github.com/eclipse-ecsp/androidVehicleConnectSDK.git")
                }
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("dileephemachandranharman")
                        name.set("Dileep Hemachandran")
                        email.set("dileep.hemachandran@harman.com")
                        organization.set("eclipse-ecsp")
                        organizationUrl.set("https://github.com/eclipse-ecsp")
                    }
                }
            }
        }
    }

    signing {
        useInMemoryPgpKeys(System.getenv("GPG_SUBKEY_ID"), System.getenv("GPG_PRIVATE_KEY"), System.getenv("GPG_PASSPHRASE"))
        publishing.publications.all {
            sign(this)
        }
    }
}

jreleaser {
    deploy {
        maven {
            mavenCentral {
                create("app") {
                    setActive("ALWAYS")
                    uri("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository("target/staging-deploy")
                    username = System.getenv("CENTRAL_SONATYPE_USERNAME")
                    password = System.getenv("CENTRAL_SONATYPE_PASSWORD")
                    sign = true
                }
            }
        }
    }
}

dependencies {
    val kotlinStdlibVersion: String by project
    val retrofitVersion: String by project
    val gsonVersion: String by project
    val okHttpLoggingVersion: String by project
    val appAuthVersion: String by project
    val multiDexVersion: String by project
    val coreKtxVersion: String by project
    val androidxConcurrentVersion: String by project
    val lifeCycleCommonVersion: String by project
    val daggerVersion: String by project
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinStdlibVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpLoggingVersion")
    implementation("net.openid:appauth:$appAuthVersion")
    implementation("androidx.multidex:multidex:$multiDexVersion")
    implementation("androidx.core:core-ktx:$coreKtxVersion")

    implementation("com.google.dagger:dagger:$daggerVersion")
    implementation("com.google.dagger:dagger-android:$daggerVersion")
    implementation("com.google.dagger:dagger-android-support:$daggerVersion")
    kapt("com.google.dagger:dagger-android-processor:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
}
